package com.fmkj.tools;

/**
 * Created by 123 on 2018/1/10.
 */

public class Adpcm {
    public static byte[] IndexTable = new byte[] { -1, -1, -1, -1, 2, 4, 6, 8, -1, -1, -1, -1, 2, 4, 6, 8 };
    public static short[] stepsizeTable = new short[]{
            7, 8, 9, 10, 11, 12, 13, 14, 16, 17,
            19, 21, 23, 25, 28, 31, 34, 37, 41, 45,
            50, 55, 60, 66, 73, 80, 88, 97, 107, 118,
            130, 143, 157, 173, 190, 209, 230, 253, 279, 307,
            337, 371, 408, 449, 494, 544, 598, 658, 724, 796,
            876, 963, 1060, 1166, 1282, 1411, 1552, 1707, 1878, 2066,
            2272, 2499, 2749, 3024, 3327, 3660, 4026, 4428, 4871, 5358,
            5894, 6484, 7132, 7845, 8630, 9493, 10442, 11487, 12635, 13899,
            15289, 16818, 18500, 20350, 22385, 24623, 27086, 29794, 32767
    };
    public static int encorderIndex = 0;
    public static int encorderPredsample = 0;
    public static short decoderIndex = 0;
    public static int decoderPredsample = 0;

    /// <summary>
    /// 初始化ADPCM编码器
    /// </summary>
    public void ReSetAdpcmEncode()
    {
        encorderIndex = 0;
        encorderPredsample = 0;
    }

    /// <summary>
    /// 初始化ADPCM解码器
    /// </summary>
    public void ReSetAdpcmDecode()
    {
        decoderIndex = 0;
        decoderPredsample = 0;
    }

    public byte[] PCMToADPCM(byte[] pcmData, int len)
    {
        byte[] adpcmData = new byte[len/4];
        int dataL = 0,dataH = 0;
        int index = 0;
        int j = 0;
        byte adpcm=0;
        int temp;

        for (int i = 0; i < len; i +=4)
        {
            for(j=0;j<4;j++) {
                if (j==0)  {
          	      temp = pcmData[i+j];
          	     if(pcmData[i+j]<0)
          		     temp += 256;
          	               dataL = temp;
          	      }
                else if(j==1) dataL += pcmData[i+j]<<8;
                else if(j==2) {
          	      temp = pcmData[i+j];
          	      if(pcmData[i+j]<0)
          		     temp += 256;
          	               dataH = temp;
          	      }
                else if(j==3) dataH += pcmData[i+j]<<8;
            }
            adpcm = ADPCM_Encode(dataL);
            adpcm += ADPCM_Encode(dataH)<<4;
            adpcmData[index++] = adpcm;
        }
        return adpcmData;
    }

    public   byte ADPCM_Encode(int pcm)
    {
        byte code = 0;
        short tmpstep = 0;
        int diff = 0;
        int diffq = 0;
        short step = 0;

        step = stepsizeTable[encorderIndex];
            /* 2. compute diff and record sign and absolut value */
        diff = pcm - encorderPredsample;
        if (diff < 0)
        {
            code = 8;
            diff = -diff;
        }

            /* 3. quantize the diff into ADPCM code */
            /* 4. inverse quantize the code into a predicted diff */
        tmpstep = step;
        diffq = (step >> 3);

        if (diff >= tmpstep)
        {
            code |= 0x04;
            diff -= tmpstep;
            diffq += step;
        }

        tmpstep = (short)(tmpstep >> 1);
        if (diff >= tmpstep)
        {
            code |= 0x02;
            diff -= tmpstep;
            diffq += (step >> 1);
        }

        tmpstep = (short)(tmpstep >> 1);

        if (diff >= tmpstep)
        {
            code |= 0x01;
            diffq += (step >> 2);
        }

            /* 5. fixed predictor to get new predicted sample*/
        if ((code & 0x08) > 0)
        {
            encorderPredsample -= diffq;
        }
        else
        {
            encorderPredsample += diffq;
        }

            /* check for overflow*/
        if (encorderPredsample > 32767)
        {
            encorderPredsample = 32767;
        }
        else if (encorderPredsample < -32768)
        {
            encorderPredsample = -32768;
        }

            /* 6. find new stepsize index */
        encorderIndex += IndexTable[code];
            /* check for overflow*/
        if (encorderIndex < 0)
        {
            encorderIndex = 0;
        }
        else if (encorderIndex > 88)
        {
            encorderIndex = 88;
        }

            /* 8. return new ADPCM code*/
        return (byte)(code & 0x0f);
    }
    /// <summary>
    /// ADPCM转PCM
    /// </summary>
    /// <param name="adpcmData">adpcm字节流</param>
    /// <param name="len">adpcm字节流长度</param>
    /// <returns></returns>
    public  byte[] ADPCMToPCM(byte[] adpcmData, int len)
    {
        byte[] pcmData = new byte[4 * len];
        int i, j, data, data1;
        short step = 0;
        int diffq = 0;
        int adpcmh, adpcml;

        for (i = 0; i < len; i++)
        {
            data1 = adpcmData[i] & 0xff;
            for (j = 0; j < 2; j++)
            {
                if (j == 0)
                {
                    data = (data1 & 0x0000000f);//取低4-bit
                    adpcmh = 4 * i;
                    adpcml = adpcmh + 1;
                }
                else
                {
                    data = ((data1 >> 4) & 0x0000000f);//取高4-bit
                    adpcmh = 4 * i + 2;
                    adpcml = adpcmh + 1;
                }
                step = stepsizeTable[decoderIndex];

                    /* 2. inverse code into diff */
                diffq = step >> 3;
                if ((data & 0x00000004) > 0)
                {
                    diffq += step;
                }

                if ((data & 0x00000002) > 0)
                {
                    diffq += step >> 1;
                }

                if ((data & 0x00000001) > 0)
                {
                    diffq += step >> 2;
                }

                    /* 3. add diff to predicted sample*/
                if ((data & 0x00000008) > 0)
                {
                    decoderPredsample -= diffq;
                }
                else
                {
                    decoderPredsample += diffq;
                }

                    /* check for overflow*/
                if (decoderPredsample > 32767)
                {
                    decoderPredsample = 32767;
                }
                else if (decoderPredsample < -32768)
                {
                    decoderPredsample = -32768;
                }

                    /* 4. find new quantizer step size */
                decoderIndex += IndexTable[data];
                    /* check for overflow*/
                if (decoderIndex < 0)
                {
                    decoderIndex = 0;
                }
                if (decoderIndex > 88)
                {
                    decoderIndex = 88;
                }
                pcmData[adpcmh] = (byte)((decoderPredsample) & 0x00ff);
                pcmData[adpcml] = (byte)(((decoderPredsample) >> 8) & 0x00ff);
            }
        }
        return pcmData;
    }
}
