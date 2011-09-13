package start.pleer;// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3)
// Source File Name:   start.pleer.IDCT.java


public class IDCT
{

    IDCT()
    {
        matr1 = new int[64];
        matr2 = new int[64];
        for(int i = 0; i < 64; i++)
        {
            for(int j = 0; j < 64; j++)
                IDFT_table[i][j] = 0;

            IDFT_table[i][i] = 2048;
            invers_dct(IDFT_table[i]);
            for(int k = 0; k < 8; k++)
            {
                for(int l = 0; l < 8; l++)
                    IDFT_table[i][k * 8 + l] = (int)((double)IDFT_table[i][k * 8 + l] * Math.cos((3.1415926535897931D * (double)k) / 16D) * Math.cos((3.1415926535897931D * (double)l) / 16D));

            }

        }

    }

    public void norm(int ai[])
    {
        for(int j = 0; j < 8; j++)
        {
            for(int i = 0; i < 8; i++)
            {
                double d = ai[j * 8 + i];
                if(i == 0 && j == 0)
                    d /= 8D;
                else
                if(i == 0 || j == 0)
                    d /= 8D / Math.sqrt(2D);
                else
                    d /= 4D;
                ai[j * 8 + i] = (int)(d * 2048D * Math.cos((3.1415926535897931D * (double)i) / 16D) * Math.cos((3.1415926535897931D * (double)j) / 16D) + 0.5D);
            }

        }

    }

    public void invers_dct_special(int ai[], int i)
    {
        if(i == 0)
        {
            int j = ai[0] >> 11;
            for(int i1 = 0; i1 < 64;)
                ai[i1++] = j;

            return;
        }
        int k = ai[i];
        int ai1[] = IDFT_table[i++];
        boolean flag = false;
        for(int l = 0; l < 64; l++)
            ai[l] = ai1[l] * k >> 9;

    }

    public void invers_dct(int ai[])
    {
        int i19 = 0;
        int j19 = 0;
        int k19 = 0;
        int l19 = 0;
        int l21;
        int i22 = l21 = 0;
        for(; l21 < 64; l21 += 8)
        {
            matr1[i22++] = ai[l21 + 0];
            matr1[i22++] = ai[l21 + 4];
            int i8;
            int l11;
            matr1[i22++] = (i8 = ai[l21 + 2]) - (l11 = ai[l21 + 6]);
            matr1[i22++] = i8 + l11;
            int j9;
            int k10;
            matr1[i22++] = -(j9 = ai[l21 + 3]) + (k10 = ai[l21 + 5]);
            int l6;
            int i13;
            int j14;
            int k15;
            matr1[i22++] = (k15 = l6 = ai[l21 + 1] + (i13 = ai[l21 + 7])) - (j14 = j9 + k10);
            matr1[i22++] = l6 - i13;
            matr1[i22++] = k15 + j14;
        }

        int i21;
        i22 = i21 = 0;
        for(; i21 < 8; i21++)
            switch(i21)
            {
            case 0: // '\0'
            case 1: // '\001'
            case 3: // '\003'
            case 7: // '\007'
                int k9;
                int l10;
                int k1 = (k9 = matr1[24 + i21]) - (l10 = matr1[40 + i21]);
                int i7;
                int j13;
                int j3 = (i7 = matr1[8 + i21]) - (j13 = matr1[56 + i21]);
                int j18 = 1567 * (j3 - k1);
                matr2[i22++] = matr1[i21] << 11;
                matr2[i22++] = matr1[32 + i21] << 11;
                int j8;
                int i12;
                matr2[i22++] = ((j8 = matr1[16 + i21]) - (i12 = matr1[48 + i21])) * 2896;
                matr2[i22++] = j8 + i12 << 11;
                matr2[i22++] = 2217 * k1 - j18;
                int k14;
                int l15;
                matr2[i22++] = ((l15 = i7 + j13) - (k14 = k9 + l10)) * 2896;
                matr2[i22++] = 5352 * j3 - j18;
                matr2[i22++] = l15 + k14 << 11;
                break;

            case 2: // '\002'
            case 5: // '\005'
                int l9;
                int i11;
                int l1 = (l9 = matr1[24 + i21]) - (i11 = matr1[40 + i21]);
                int j7;
                int k13;
                int k3 = (j7 = matr1[8 + i21]) - (k13 = matr1[56 + i21]);
                int k18 = 2217 * (k3 - l1);
                matr2[i22++] = 2896 * matr1[i21];
                matr2[i22++] = 2896 * matr1[i21 + 32];
                int k8;
                int j12;
                matr2[i22++] = (k8 = matr1[16 + i21]) - (j12 = matr1[48 + i21]) << 12;
                matr2[i22++] = 2896 * (k8 + j12);
                matr2[i22++] = 3135 * l1 - k18;
                int l14;
                int i16;
                matr2[i22++] = (i16 = j7 + k13) - (l14 = l9 + i11) << 12;
                matr2[i22++] = 7568 * k3 - k18;
                matr2[i22++] = 2896 * (i16 + l14);
                break;

            case 4: // '\004'
                matr2[i22++] = matr1[i21];
                matr2[i22++] = matr1[32 + i21];
                int l8;
                int k12;
                matr2[i22++] = (l8 = matr1[16 + i21]) - (k12 = matr1[48 + i21]);
                matr2[i22] = l8 + k12;
                int i10;
                int j11;
                i19 = k19 = -(i10 = matr1[24 + i21]) + (j11 = matr1[40 + i21]);
                i22 += 2;
                int k7;
                int l13;
                int i15;
                int j16;
                matr2[i22] = (j16 = (k7 = matr1[8 + i21]) + (l13 = matr1[56 + i21])) - (i15 = i10 + j11);
                l19 = -(j19 = k7 - l13);
                i22 += 2;
                matr2[i22++] = j16 + i15;
                break;

            case 6: // '\006'
                matr2[i22++] = matr1[i21];
                matr2[i22++] = matr1[32 + i21];
                int i9;
                int l12;
                matr2[i22++] = (i9 = matr1[16 + i21]) - (l12 = matr1[48 + i21]);
                matr2[i22] = i9 + l12;
                int i2;
                int j10;
                int k11;
                j19 += i2 = -(j10 = matr1[24 + i21]) + (k11 = matr1[40 + i21]);
                l19 += i2;
                i22 += 2;
                int l7;
                int i14;
                int j15;
                int k16;
                matr2[i22] = (k16 = (l7 = matr1[8 + i21]) + (i14 = matr1[56 + i21])) - (j15 = j10 + k11);
                int l3;
                k19 += l3 = l7 - i14;
                i19 -= l3;
                i22 += 2;
                matr2[i22++] = k16 + j15;
                break;
            }

        int i20 = 2896 * (i19 + j19);
        int j20 = 2896 * (i19 - j19);
        int k20 = k19 << 12;
        int l20 = l19 << 12;
        matr2[36] = i20 + k20;
        matr2[38] = j20 + l20;
        matr2[52] = j20 - l20;
        matr2[54] = k20 - i20;
        int l18 = 1567 * (matr2[32] + matr2[48]);
        matr2[32] = -2217 * matr2[32] - l18;
        matr2[48] = 5352 * matr2[48] - l18;
        l18 = 1567 * (matr2[33] + matr2[49]);
        matr2[33] = -2217 * matr2[33] - l18;
        matr2[49] = 5352 * matr2[49] - l18;
        l18 = 2217 * (matr2[34] + matr2[50]);
        matr2[34] = -3135 * matr2[34] - l18;
        matr2[50] = 7568 * matr2[50] - l18;
        l18 = 1567 * (matr2[35] + matr2[51]);
        matr2[35] = -2217 * matr2[35] - l18;
        matr2[51] = 5352 * matr2[51] - l18;
        l18 = 2217 * (matr2[37] + matr2[53]);
        matr2[37] = -3135 * matr2[37] - l18;
        matr2[53] = 7568 * matr2[53] - l18;
        l18 = 1567 * (matr2[39] + matr2[55]);
        matr2[39] = -2217 * matr2[39] - l18;
        matr2[55] = 5352 * matr2[55] - l18;
        int j21;
        for(int j22 = j21 = 0; j21 < 8; j22 += 8)
        {
            int j2;
            int l17;
            matr1[j22] = (j2 = (l17 = matr2[j22] + matr2[j22 + 1]) + matr2[j22 + 3]) + matr2[j22 + 7];
            int i;
            int k;
            int i4;
            int k4;
            matr1[j22 + 3] = (i4 = l17 - matr2[j22 + 3]) - (k4 = matr2[j22 + 4] - (i = (k = matr2[j22 + 6] - matr2[j22 + 7]) - matr2[j22 + 5]));
            matr1[j22 + 4] = i4 + k4;
            int i1;
            int l16;
            int j17;
            matr1[j22 + 1] = (i1 = (l16 = matr2[j22] - matr2[j22 + 1]) + (j17 = matr2[j22 + 2] - matr2[j22 + 3])) + k;
            int l2;
            matr1[j22 + 2] = (l2 = l16 - j17) - i;
            matr1[j22 + 5] = l2 + i;
            matr1[j22 + 6] = i1 - k;
            matr1[j22 + 7] = j2 - matr2[j22 + 7];
            j21++;
        }

        int i5 = 8;
        int j5 = 16;
        int k5 = 24;
        int l5 = 32;
        int i6 = 40;
        int j6 = 48;
        int k6 = 56;
        int k21;
        for(int k22 = k21 = 0; k22 < 64; k22 += 8)
        {
            int k2;
            int i18;
            ai[k22] = (k2 = (i18 = matr1[k21] + matr1[i5]) + matr1[k5]) + matr1[k6] >> 22;
            int j;
            int l;
            int j4;
            int l4;
            ai[k22 + 3] = (j4 = i18 - matr1[k5]) - (l4 = matr1[l5++] - (j = (l = matr1[j6++] - matr1[k6]) - matr1[i6++])) >> 22;
            ai[k22 + 4] = j4 + l4 >> 22;
            int j1;
            int i17;
            int k17;
            ai[k22 + 1] = (j1 = (i17 = matr1[k21++] - matr1[i5++]) + (k17 = matr1[j5++] - matr1[k5++])) + l >> 22;
            int i3;
            ai[k22 + 2] = (i3 = i17 - k17) - j >> 22;
            ai[k22 + 5] = i3 + j >> 22;
            ai[k22 + 6] = j1 - l >> 22;
            ai[k22 + 7] = k2 - matr1[k6++] >> 22;
        }

    }

    private final int CONST_BITS = 11;
    public final int VAL_BITS = 11;
    private final int ALLBITS = 22;
    private final int TWO = 12;
    private int matr1[];
    private int matr2[];
    private static int IDFT_table[][] = new int[64][64];

}
