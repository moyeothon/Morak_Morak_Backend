function chat(debateRoomId, username) {
    const form = document.getElementById("chat-form");
    const formData = new FormData(form);

    const chatDto = {
        type: 'TALK',
        roomId: debateRoomId,
        sender: username,
        message: formData.get('message')
    };

    stompClient.send('/pub/chat/rooms', {}, JSON.stringify(chatDto));
}
