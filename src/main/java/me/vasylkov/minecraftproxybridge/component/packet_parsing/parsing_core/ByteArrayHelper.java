package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import org.springframework.stereotype.Component;

import java.nio.ByteBuffer;

@Component
public class ByteArrayHelper {
    public byte[] merge(byte[]... arrays) {
        if (arrays == null || arrays.length == 0) {
            throw new IllegalArgumentException("Необходимо предоставить хотя бы один массив для объединения.");
        }

        int totalLength = countTotalLength(arrays);
        ByteBuffer buffer = mergeArraysToBuffer(totalLength, arrays);

        return buffer.array();
    }

    public int countTotalLength(byte[]... arrays) {
        int totalLength = 0;
        for (byte[] array : arrays) {
            if (array != null) { // Игнорируем null массивы
                totalLength += array.length;
            }
        }
        return totalLength;
    }

    private ByteBuffer mergeArraysToBuffer(int totalLength, byte[]... arrays) {
        ByteBuffer buffer = ByteBuffer.allocate(totalLength);
        for (byte[] array : arrays) {
            if (array != null) {
                buffer.put(array);
            }
        }
        return buffer;
    }
}
