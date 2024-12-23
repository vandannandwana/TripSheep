package com.vandan.tripsheep.data.dto

import com.vandan.tripsheep.data.local.MessageResponse

data class MessageResponseDto (
    val message: String
)

fun MessageResponseDto.toMessageResponse(messageDto:MessageResponseDto):MessageResponse{
    return MessageResponse(message = messageDto.message)
}