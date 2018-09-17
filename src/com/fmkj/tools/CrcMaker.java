package com.fmkj.tools;

/**
 * Created by 123 on 2018/1/9.
 */

public class CrcMaker {
	int Crc16;
	int GENP = 0xA001;

	public boolean CRC16Check(byte[] buf, int len) {
		Crc16 = 0x0000ffff;
		CalCrc16(buf, len - 2);
		if (((Crc16 & 0x000000ff) == buf[len - 2]) && ((Crc16 >> 8) == buf[len - 1]))
			return true;
		else
			return false;
	}

	private int CalCrc16(byte[] buf, int len) {
		int i;
		Crc16 = 0x0000ffff;
		for (i = 0; i < len; i++)
			CRC16((int) buf[i]);
		return Crc16;
	}
	
	public void setCrc(byte[] buf, int len) {
		int i;
		Crc16 = 0x0000ffff;
		for (i = 0; i < len - 2; i++)
			CRC16((int) buf[i]);
		buf[len - 2] = (byte) ((Crc16 & 0x0000FFFF));
		buf[len - 1] = (byte) ((Crc16 & 0x0000FFFF) >> 8);
	}

	private void CRC16(int value) {
		int i, temp = 0;
		if (value < 0)
			value += 256;
		Crc16 ^= value;
		for (i = 0; i < 8; i++) {
			temp = Crc16 & 0x00000001;
			Crc16 >>= 1;
			Crc16 &= 0x00007fff;
			if (temp > 0)
				Crc16 ^= GENP;
		}
	}
}
