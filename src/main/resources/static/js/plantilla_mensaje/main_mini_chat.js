'use strict';

var id_user = document.querySelector('#id_user');

var stompClient = null;
var username = null;

var colors = [
    '#2196F3', '#32c787', '#00BCD4', '#ff5652',
    '#ffc107', '#ff85af', '#FF9800', '#39bbb0'
];

function connect(event) {
    username = document.querySelector('#username').value.trim();

    if(username) {
    	
        var socket = new SockJS('/ws');
        stompClient = Stomp.over(socket);

        stompClient.connect({}, onConnected, onError);
    }
    event.preventDefault();
}


function onConnected() {
    // Se suscribe al canal que necesita
	var id_user = document.querySelector('#id_user').value.trim();
    stompClient.subscribe('/topic/'+id_user, onMessageReceived);

    // Tell your username to the server
    stompClient.send("/app/chat.addUser/"+id_user,
        {},
        JSON.stringify({sender: username, type: 'JOIN'})
    )

}


function onError(error) {
    connectingElement.textContent = 'Could not connect to WebSocket server. Please refresh this page to try again!';
    connectingElement.style.color = 'red';
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var id_user_enviar = document.querySelector('#enviar').value.trim();
    var id_user = document.querySelector('#id_user').value.trim();

    if(messageContent && stompClient) {
        var chatMessage = {
            sender: username,
            content: messageInput.value,
            type: 'CHAT',
            user_from: id_user
        };

        stompClient.send("/app/chat.sendMessage/"+id_user_enviar, {}, JSON.stringify(chatMessage));
        //mandamos el mensaje, ahora hay que mostrar lo que mandamos
        var messageElement = document.createElement('li');
        messageElement.classList.add('chat-message');

        var avatarElement = document.createElement('i');
        var avatarText = document.createTextNode(username[0]);
        avatarElement.appendChild(avatarText);
        avatarElement.style['background-color'] = getAvatarColor(username);

        messageElement.appendChild(avatarElement);

        var usernameElement = document.createElement('span');
        var usernameText = document.createTextNode(username);
        usernameElement.appendChild(usernameText);
        messageElement.appendChild(usernameElement);
        var textElement = document.createElement('p');
        var messageText = document.createTextNode(messageInput.value);
        textElement.appendChild(messageText);

        messageElement.appendChild(textElement);

        messageArea.appendChild(messageElement);
        messageArea.scrollTop = messageArea.scrollHeight;
        //aqui terminamos de mostrar lo que mandamos
        messageInput.value = '';
    }
    event.preventDefault();
}


function onMessageReceived(payload) {
    var message = JSON.parse(payload.body);

    var messageElement = document.createElement('li');

    if(message.type === 'JOIN') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' joined!';
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
    	var username_to = document.querySelector('#usernamereceptor').value.trim();
    	var username_sender = message.sender;
    	console.log(username_to);
    	console.log(username_sender);
    	if(username_sender.trim() == username_to){
    		var messageArea = document.getElementById('lista_mensajes');
    		var messageElement = document.createElement('li');
    		messageElement.id="id_lista";
            messageElement.classList.add('replies');
            var textElement = document.createElement('p');
            var messageText = document.createTextNode(message.content);
            textElement.appendChild(messageText);

            messageElement.appendChild(textElement);
            
            messageArea.appendChild(messageElement);
    	}else{
    		console.log("tienes un mensaje de"+message.sender);
    	}
    }

    $(".messages").animate({ scrollTop: $(document).height() + 90000 }, "fast");
}


function getAvatarColor(messageSender) {
    var hash = 0;
    for (var i = 0; i < messageSender.length; i++) {
        hash = 31 * hash + messageSender.charCodeAt(i);
    }

    var index = Math.abs(hash % colors.length);
    return colors[index];
}

usernameForm.addEventListener('submit', connect, true)
messageForm.addEventListener('submit', sendMessage, true)
