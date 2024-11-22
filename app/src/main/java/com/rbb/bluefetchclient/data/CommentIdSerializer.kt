package com.rbb.bluefetchclient.data

import kotlinx.serialization.KSerializer
import kotlinx.serialization.SerializationException
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder

object CommentIdSerializer : KSerializer<String> {

    override val descriptor: SerialDescriptor =
        PrimitiveSerialDescriptor("MixedTypeString", PrimitiveKind.STRING)

    override fun serialize(encoder: Encoder, value: String) {
        encoder.encodeString(value)
    }

    override fun deserialize(decoder: Decoder): String {
        return when (val element = decoder.decodeStringOrInt()) {
            is String -> element
            is Int -> element.toString()
            else -> throw SerializationException("Unexpected type for id: $element")
        }
    }

    private fun Decoder.decodeStringOrInt(): Any {
        return try {
            decodeInt()
        } catch (e: SerializationException) {
            decodeString()
        }
    }
}