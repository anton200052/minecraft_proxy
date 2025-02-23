package me.vasylkov.minecraftproxybridge.component.packet_parsing.parsing_core;

import org.springframework.stereotype.Component;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

@Component
public class PacketDataCodec {
    public int readVarInt(InputStream in) throws IOException {
        int numRead = 0;
        int result = 0;
        byte read;
        do {
            int current = in.read();
            if (current == -1) {
                throw new IOException("Конец потока достигнут до завершения VarInt.");
            }

            read = (byte) current;
            int value = (read & 0b01111111);
            result |= (value << (7 * numRead));

            numRead++;
            if (numRead > 5) {
                throw new RuntimeException("VarInt слишком длинный (больше 5 байт).");
            }
        } while ((read & 0b10000000) != 0);

        return result;
    }

    public byte[] encodeVarInt(int value) throws IOException {
        try (ByteArrayOutputStream out = new ByteArrayOutputStream()) {
            writeVarInt(value, out);
            return out.toByteArray();
        }
    }

    public void writeVarInt(int value, OutputStream out) throws IOException {
        while (true) {
            if ((value & ~0x7F) == 0) {
                out.write(value);
                return;
            }

            out.write((value & 0x7F) | 0x80);
            value >>>= 7;
        }
    }

    public String readString(InputStream in, int length) throws IOException {
        byte[] buffer = new byte[length];
        int bytesRead = 0;
        while (bytesRead < length) {
            int readCount = in.read(buffer, bytesRead, length - bytesRead);
            if (readCount == -1) {
                throw new IOException("Конец потока достигнут до завершения чтения строки.");
            }
            bytesRead += readCount;
        }
        return new String(buffer, StandardCharsets.UTF_8);
    }

    public byte[] encodeString(String s) {
        return s.getBytes(StandardCharsets.UTF_8);
    }

    public int readUnsignedShort(InputStream in) throws IOException {
        int high = in.read();
        int low = in.read();
        if (high < 0 || low < 0) {
            throw new IOException("Конец потока достигнут до завершения чтения UnsignedShort.");
        }
        return (high << 8) | low;
    }

    public byte[] encodeUnsignedShort(int value) throws IOException {
        if (value < 0 || value > 65535) {
            throw new IOException("Value out of range for unsigned short: " + value);
        }
        byte high = (byte) ((value >>> 8) & 0xFF);
        byte low = (byte) (value & 0xFF);
        return new byte[] {high, low};
    }

    public long readLong(InputStream in) throws IOException {
        byte[] bytes = new byte[8];
        int bytesRead = 0;
        while (bytesRead < 8) {
            int readCount = in.read(bytes, bytesRead, 8 - bytesRead);
            if (readCount < 0) {
                throw new IOException("Конец потока достигнут до завершения чтения Long (8 байт).");
            }
            bytesRead += readCount;
        }

        long value = 0;
        for (int i = 0; i < 8; i++) {
            value = (value << 8) | (bytes[i] & 0xFF);
        }
        return value;
    }

    public byte[] encodeLong(long value) {
        byte[] result = new byte[8];
        for (int i = 7; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }
        return result;
    }

    public UUID readUUID(InputStream in) throws IOException {
        long mostSigBits = readLong(in);
        long leastSigBits = readLong(in);
        return new UUID(mostSigBits, leastSigBits);
    }

    public byte[] encodeUUID(UUID uuid) throws IOException {
        ByteArrayOutputStream out = new ByteArrayOutputStream(16);
        out.write(encodeLong(uuid.getMostSignificantBits()));
        out.write(encodeLong(uuid.getLeastSignificantBits()));
        return out.toByteArray();
    }

    public boolean readBoolean(InputStream in) throws IOException {
        int readValue = in.read();
        if (readValue == -1) {
            throw new IOException("Конец потока достигнут до завершения чтения boolean.");
        }
        return readValue != 0;
    }

    public byte[] encodeBoolean(boolean value) {
        return new byte[]{(byte) (value ? 1 : 0)};
    }

    public double readDouble(InputStream in) throws IOException {
        long longBits = readLong(in);
        return Double.longBitsToDouble(longBits);
    }

    public byte[] encodeDouble(double value) {
        long longBits = Double.doubleToRawLongBits(value);
        return encodeLong(longBits);
    }

    public byte readByte(InputStream in) throws IOException {
        int value = in.read();
        if (value == -1) {
            throw new IOException("Конец потока достигнут до завершения чтения байта.");
        }
        return (byte) value;
    }

    public byte[] encodeByte(byte value) {
        return new byte[]{value};
    }

    public int readInt(InputStream in) throws IOException {
        byte[] bytes = new byte[4];
        int bytesRead = 0;
        while (bytesRead < 4) {
            int readCount = in.read(bytes, bytesRead, 4 - bytesRead);
            if (readCount < 0) {
                throw new IOException("Конец потока достигнут до завершения чтения Int (4 байта).");
            }
            bytesRead += readCount;
        }

        int value = 0;
        for (int i = 0; i < 4; i++) {
            value = (value << 8) | (bytes[i] & 0xFF);
        }
        return value;
    }

    public byte[] encodeInt(int value) {
        byte[] result = new byte[4];
        for (int i = 3; i >= 0; i--) {
            result[i] = (byte) (value & 0xFF);
            value >>>= 8;
        }
        return result;
    }

    public float readFloat(InputStream in) throws IOException {
        int bits = readInt(in);
        return Float.intBitsToFloat(bits);
    }

    public byte[] encodeFloat(float value) {
        int bits = Float.floatToIntBits(value);
        return encodeInt(bits);
    }

}
