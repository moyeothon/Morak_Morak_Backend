let socket = new SockJS("/ws");
let stompClient = Stomp.over(socket);

stompClient.connect({}, function(frame) {
    const debateRoomId = document.getElementById("debateroom-id").value;

    stompClient.subscribe('/sub/chat/rooms/' + debateRoomId, function(msg) {
        const body = JSON.parse(msg.body);
        if (body.type === 'TALK') {
            onChatMessage(body.username, body.message);
        }
    });
});

function onChatMessage(username, message) {
    $("<div>").text(username + ' : ' + message).addClass('mb-2 debateroom__chat').appendTo("#chat-list");
    $("#chat-list").scrollTop($("#chat-list")[0].scrollHeight);
}
