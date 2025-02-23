package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.DataFormatException;
import java.util.zip.Deflater;
import java.util.zip.Inflater;

@Component
public class ZlibCompressor {
    public byte[] compressZlib(byte[] data) {
        Deflater deflater = new Deflater(Deflater.DEFAULT_COMPRESSION);
        deflater.setInput(data);
        deflater.finish();

        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[8192];
        try {
            while (!deflater.finished()) {
                int count = deflater.deflate(buffer);
                out.write(buffer, 0, count);
            }
        } finally {
            deflater.end();
        }

        return out.toByteArray();
    }

    public byte[] decompressZlib(byte[] compressedData, int uncompressedSize) throws IOException {
        Inflater inflater = new Inflater();
        inflater.setInput(compressedData);

        // Если известен uncompressedSize, можно использовать его для оптимизации размера буфера.
        ByteArrayOutputStream out = new ByteArrayOutputStream(
                uncompressedSize > 0 ? uncompressedSize : compressedData.length
        );

        byte[] buffer = new byte[8192];
        try {
            while (!inflater.finished()) {
                int count = inflater.inflate(buffer);
                if (count == 0) {
                    // Если вдруг нет прогресса, а Inflater требует больше данных
                    if (inflater.needsInput()) {
                        break;
                    }
                }
                out.write(buffer, 0, count);
            }
        } catch (DataFormatException e) {
            throw new IOException("Ошибка распаковки zlib-данных", e);
        } finally {
            inflater.end();
        }

        return out.toByteArray();
    }
}
