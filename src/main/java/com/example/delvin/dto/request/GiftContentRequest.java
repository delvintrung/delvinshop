package com.example.delvin.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Setter;

@Data
@Setter
@AllArgsConstructor
public class GiftContentRequest {
    private String senderName;
    private String recipientName;
    private String title;
    private String message;
    private String themeColor;
    private String backgroundMusic;
    private String imageUrl;

}
