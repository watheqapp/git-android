let functions = require('firebase-functions');
let admin = require('firebase-admin');
admin.initializeApp(functions.config().firebase);

exports.sendNotification = functions.database.ref('/notifications/messages/{pushId}')
    .onWrite(event => {
        const message = event.data.current.val();
        const senderUid = message.from;
        const receiverUid = message.to;
        const promises = [];

        if (senderUid === receiverUid) {
            //if sender is receiver, don't send notification
            promises.push(event.data.current.ref.remove());
            return Promise.all(promises);
        }

        //const getInstanceIdPromise = admin.database().ref(`/users/${receiverUid}`).once('value');
        //const getRecevierPromise = admin.database().ref(`/users/${receiverUid}`).once('value');
		const getInstanceIdPromise = admin.database().ref(`/users/${receiverUid}/instanceId`).once('value');
		const getDevicePromise = admin.database().ref(`/users/${receiverUid}/device`).once('value');
        //const getReceiverUidPromise = admin.auth().getUser(receiverUid);

		/*
		const getSenderUidPromise = admin.auth().getUser(receiverUid).then(function(userRecord) {
				// See the UserRecord reference doc for the contents of userRecord.
				console.log("Successfully fetched user data:", userRecord.toJSON());
			}).catch(function(error) {
    console.log("Error fetching user data:", error);
  });
  */
        return Promise.all([getInstanceIdPromise,getDevicePromise]).then(results => {
			console.log('testing ... ' + JSON.stringify(results));
			const instanceId = results[0].val();
            const device = results[1].val();
			console.log('notifying ' + receiverUid + ' about ' + message.body + ' from ' + senderUid+' device ' +device);

            const payload = {
                data: {
                    title: message.displayName,
                    content: message.body,
                    senderId:message.from,
                    id:message.orderId,
                    type:'message'
                }
				
            };
			
			const payload2 = {
				notification: {
                    title: message.displayName,
                    body: message.body,
					senderId: message.from,
					id: message.orderId,
					type: 'message'
                }
			};
if(device !== null && device !== "android"){
            admin.messaging().sendToDevice(instanceId, payload2)
                .then(function (response) {
                    console.log("Successfully sent message:", response);
					
                })
                .catch(function (error) {
                    console.log("Error sending message:", error);
                });
}else{
			admin.messaging().sendToDevice(instanceId, payload)
                .then(function (response) {
                    console.log("Successfully sent message:", response);
					
                })
                .catch(function (error) {
                    console.log("Error sending message:", error);
                });
}
        });
    });