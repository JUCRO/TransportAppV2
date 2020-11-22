package com.example.androiduberriderremake;

public class Message {
    private String messageRecived, name;

    public Message() {
    }

    public Message(String messageRecived, String name) {
        this.messageRecived = messageRecived;
        this.name = name;
    }

    public String getMessageRecived() {
        return messageRecived;
    }

    public void setMessageRecived(String messageRecived) {
        this.messageRecived = messageRecived;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
