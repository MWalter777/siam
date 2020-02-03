'use strict';

var usernamePage = document.querySelector('#username-page');
var chatPage = document.querySelector('#chat-page');
var usernameForm = document.querySelector('#usernameForm');
var messageForm = document.querySelector('#messageForm');
var messageInput = document.querySelector('#message');
var messageArea = document.querySelector('#messageArea');
var connectingElement = document.querySelector('.connecting');
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
        usernamePage.classList.add('hidden');
        chatPage.classList.remove('hidden');

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
    console.log("no se puede conectar al socket");
    var error_socket = document.querySelector('#error_socket');
    error_socket.hidden=null;
}


function sendMessage(event) {
    var messageContent = messageInput.value.trim();
    var id_user_enviar = document.querySelector('#enviar').value.trim();
    var id_user = document.querySelector('#id_user').value.trim();

    console.log("id"+id_user_enviar);
    var valor = '#cantidad_valor'+id_user_enviar;
	var id_notif_valor = document.querySelector(valor);
	id_notif_valor.value = 0;
	var newValor = '#cantidad'+id_user_enviar;
	var cant = '('+id_notif_valor.value+')'
	$(newValor).text(cant);
    
    
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
        message.content = message.sender + ' joined!';
        console.log(message.sender);
    } else if (message.type === 'LEAVE') {
        messageElement.classList.add('event-message');
        message.content = message.sender + ' left!';
    } else {
    	var username_to = document.querySelector('#usernamereceptor').value.trim();
    	var username_sender = message.sender;
    	console.log(username_to);
    	console.log(username_sender);
    	console.log("id="+message.user_from);
    	var valor = '#cantidad_valor'+message.user_from;
    	var id_notif_valor = document.querySelector(valor);
    	id_notif_valor.value = parseInt(id_notif_valor.value) + 1;
    	var newValor = '#cantidad'+message.user_from;
    	var cant = '('+id_notif_valor.value+')'
    	$(newValor).text(cant);
    	
    	
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




	  	function consumirJSonUsuarios() {
	
		    var url = "service_rest/all_user";
	        $.get(url,function(data,status){
	            for(let i=0;i<data.length;i++){
	                var valor = data[i];
	            	$('<li class="contact" id="lista_contact" onclick="quitar('+valor.id+',\''+valor.username+'\')"><div class="wrap"><span class="contact-status online"></span><img src="/images/users.png" alt="" /><div class="meta"><p class="name">'+valor.username+'<strong id="cantidad'+valor.id+'">('+valor.cantidad+')</strong> <input type="number" id="cantidad_valor'+valor.id+'" value="'+valor.cantidad+'" hidden="" /> </p><p class="preview">'+valor.direccion+'</p></div></div></li>').appendTo($('#contacts ul'));
	            }
	        });
		}

		consumirJSonUsuarios();
      
		function conectar() {
		    username = document.querySelector('#username').value.trim();
		    if(username) {
		    	var socket = new SockJS('/ws');
		    	stompClient = Stomp.over(socket);	
		    	stompClient.connect({}, onConnected, onError);
		    }
		}
    	
    	conectar();
    	
    	
    	function search_user(event){
    		if(event.which == 13){
    			$('li').remove('#lista_contact');
        		var user_search = document.getElementById('user_search');        		
        		var url = "service_rest/get_user/"+user_search.value.trim()+"/valor";
    	        $.get(url,function(data,status){
    	            for(let i=0;i<data.length;i++){
    	                var valor = data[i];
    	            	$('<li class="contact" id="lista_contact" onclick="quitar('+valor.id+',\''+valor.username+'\')"><div class="wrap"><span class="contact-status online"></span><img src="/images/users.png" alt="" /><div class="meta"><p class="name">'+valor.username+'</p><p class="preview">'+valor.direccion+'</p></div></div></li>').appendTo($('#contacts ul'));
    	            }
    	        });
    		}else{
    			var user_search = document.getElementById('user_search');
    			user_search.text(user_search.value+event.witch);
    		}
    		event.preventDefault();
    	}
    	
		var user_search = document.getElementById('user_search');
		user_search.addEventListener('keydown', search_user, true);
		
    	
    	function consumirJSon() {
    	    var id_user_enviar = document.getElementById('enviar').value.trim();
    	    var id_user = document.getElementById('id_user').value.trim();
    	    var url = "service_rest/all_messages/"+id_user_enviar+"/"+id_user;
            var messageArea = document.getElementById('lista_mensajes');
            var yo = document.getElementById('username').value.trim();
            $.get(url,function(data,status){
                for(let i=0;i<data.length;i++){
                    var valor = data[i];
                    
                    /*
		                <li class="sent">
							<img src="http://emilcarlsson.se/assets/mikeross.png" alt="" />
							<p>How the hell am I supposed to get a jury to believe you when I am not even sure that I do?!</p>
						</li>
                    */
                    
                    var messageElement = document.createElement('li');
                    messageElement.id="id_lista";
                    if( yo == valor.username_emisor ){
                    	messageElement.classList.add('sent');
                    }else{
                    	messageElement.classList.add('replies');
                    }
                    var textElement = document.createElement('p');
                    var messageText = document.createTextNode(valor.contenido);
                    textElement.appendChild(messageText);

                    messageElement.appendChild(textElement);
                    
                    messageArea.appendChild(messageElement);
                }
            });
            
            $(".messages").animate({ scrollTop: $(document).height() + 90000 }, "fast");
		}
    	
    	function quitar(id, username) {
    		//lista_mensajes
    		var id_user_enviar = document.getElementById('enviar');
    		var yo = document.getElementById('username').value.trim();
    	    var username_to = document.getElementById('usernamereceptor');
    	    $('#user_to_head').text(""+username);
    	    id_user_enviar.value = ""+id;
    	    username_to.value = ""+username;
    		$('li').remove('#id_lista');
    		
    	    if(stompClient) {
    	        var chatMessage = {
    	        	sender: yo,
    	        	type: 'JOIN',
    	        };

    	        stompClient.send("/app/chat.addUser/"+id_user_enviar.value.trim(), {}, JSON.stringify(chatMessage));
    	        //mandamos el mensaje, ahora hay que mostrar lo que mandamos
    	    }
    		
    	    consumirJSon();
		}











