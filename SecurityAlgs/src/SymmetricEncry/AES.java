package SymmetricEncry;
public class AES {

    private final int Nb = 4;
    private int Nk;
    private int Nr;
    private int[] schedule;
    private byte[][] state;
    private static byte[] key;

    public AES(byte[] key) {
        int keyBits = key.length * 8;
        if (keyBits != 128 && keyBits != 192 && keyBits != 256) {
            throw new RuntimeException("Invalid AES key size (" + keyBits + " bits)");
        }
        Nk = keyBits >>> 5;
        Nr = Nk + 6;
        AES.key = key;
        schedule = new int[Nb * (Nr + 1)];
        keyExpansion();
    }

    private int RotWord(int i) {
        return Integer.rotateLeft(i, 8);
    }

    private int SubWord(int i) {
        return ((util.SBox.sub((i >>> 24))) << 24) |
                ((util.SBox.sub((i >>> 16) & 0xff) & 0xff) << 16) |
                ((util.SBox.sub((i >>> 8) & 0xff) & 0xff) << 8) |
                ((util.SBox.sub((i) & 0xff) & 0xff));
    }

    private void keyExpansion() {

        int temp;
        int[] rcon = new int[11];
        for (int i = 0, k = 0; i < Nk; i++, k += 4) {
            schedule[i] =
                    ((key[k]) << 24) |
                            ((key[k + 1] & 0xff) << 16) |
                            ((key[k + 2] & 0xff) << 8) |
                            ((key[k + 3] & 0xff));
        }

        rcon[1] = (byte) 0x01 << 24;
        rcon[2] = (byte) 0x02 << 24;
        rcon[3] = (byte) 0x04 << 24;
        rcon[4] = (byte) 0x08 << 24;
        rcon[5] = (byte) 0x10 << 24;
        rcon[6] = (byte) 0x20 << 24;
        rcon[7] = (byte) 0x40 << 24;
        rcon[8] = (byte) 0x80 << 24;
        rcon[9] = (byte) 0x1b << 24;
        rcon[10] = (byte) 0x36 << 24;

        for (int i = Nk; i < Nb * (Nr + 1); i++) {
            temp = schedule[i - 1];
            if (i % Nk == 0) {
                temp = SubWord(RotWord(temp));
                temp ^= rcon[i / Nk];
            } else if (Nk > 6 && i % Nk == 4) {
                temp = SubWord(temp);
            }
            schedule[i] = schedule[i - Nk] ^ temp;
        }
    }

    private void AddRoundKey(int round) {
        for (int c = 0; c < Nb; c++) {
            int w = schedule[4 * round + c];
            state[0][c] ^= (w >>> 24) & 0xff;
            state[1][c] ^= (w >>> 16) & 0xff;
            state[2][c] ^= (w >>> 8) & 0xff;
            state[3][c] ^= (w) & 0xff;
        }
    }

    private void SubBytes() {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++)
                state[j][i] = util.SBox.sub(state[j][i]);
        }
    }


    private void InvSubBytes() {
        for (int i = 0; i < 4; i++)
            for (int j = 0; j < 4; j++)
                state[j][i] = util.SBox.invSub(state[j][i]);
    }

    private void ShiftRows() {
        byte temp;
        for (int i = 0; i < 4; i++)
            for (int k = 0; k < i; k++) {
                temp = state[i][0];
                System.arraycopy(state[i], 1, state[i], 0, 3);
                state[i][3] = temp;
            }
    }

    private void InvShiftRows() {
        byte temp;
        for (int i = 0; i < 4; i++) {
            for (int k = 0; k < i; k++) {
                temp = state[i][3];
                System.arraycopy(state[i], 0, state[i], 1, 3);
                state[i][0] = temp;
            }
        }
    }

    private void MixColumns() {
        byte[] tempCol;
        for (int j = 0; j < 4; j++) {
            tempCol = new byte[]{state[0][j], state[1][j], state[2][j], state[3][j]};
            state[0][j] = (byte) (util.gmul2(tempCol[0]) ^ util.gmul3(tempCol[1]) ^ tempCol[2] ^ tempCol[3]);
            state[1][j] = (byte) (tempCol[0] ^ util.gmul2(tempCol[1]) ^ util.gmul3(tempCol[2]) ^ tempCol[3]);
            state[2][j] = (byte) (tempCol[0] ^ tempCol[1] ^ util.gmul2(tempCol[2]) ^ util.gmul3(tempCol[3]));
            state[3][j] = (byte) (util.gmul3(tempCol[0]) ^ tempCol[1] ^ tempCol[2] ^ util.gmul2(tempCol[3]));
        }
    }

    private void InvMixColumns() {
        byte[] tempCol;
        for (int j = 0; j < 4; j++) {
            tempCol = new byte[]{state[0][j], state[1][j], state[2][j], state[3][j]};
            state[0][j] = (byte) (util.gmul14(tempCol[0]) ^ util.gmul11(tempCol[1]) ^ util.gmul13(tempCol[2]) ^ util.gmul9(tempCol[3]));
            state[1][j] = (byte) (util.gmul9(tempCol[0]) ^ util.gmul14(tempCol[1]) ^ util.gmul11(tempCol[2]) ^ util.gmul13(tempCol[3]));
            state[2][j] = (byte) (util.gmul13(tempCol[0]) ^ util.gmul9(tempCol[1]) ^ util.gmul14(tempCol[2]) ^ util.gmul11(tempCol[3]));
            state[3][j] = (byte) (util.gmul11(tempCol[0]) ^ util.gmul13(tempCol[1]) ^ util.gmul9(tempCol[2]) ^ util.gmul14(tempCol[3]));
        }
    }

    public byte[] encrypt(byte[] input) {
        this.state = new byte[4][Nb];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                this.state[j][i] = input[4 * i + j];
            }
        }
        AddRoundKey(0);
        for (int round = 1; round < Nr; round++) {
            SubBytes();
            ShiftRows();
            MixColumns();
            AddRoundKey(round);
        }
        SubBytes();
        ShiftRows();
        AddRoundKey(Nr);
        byte[] result = new byte [state.length * state[0].length];
        for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				result[4*i+j] = this.state[j][i];
			}
		}
        return result;
    }

    public byte[] decrypt(byte[] input) {
        this.state = new byte[4][Nb];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < Nb; j++) {
                this.state[j][i] = input[4 * i + j];
            }
        }
        AddRoundKey(Nr);
        for (int round = 1; round < Nr; round++) {
            InvShiftRows();
            InvSubBytes();
            AddRoundKey(Nr - round);
            InvMixColumns();
        }
        InvShiftRows();
        InvSubBytes();
        AddRoundKey(0);
        byte[] result = new byte [state.length * state[0].length];
        for (int i = 0; i < state.length; i++) {
			for (int j = 0; j < state[i].length; j++) {
				result[4*i+j] = this.state[j][i];
			}
		}
        return result;
    }

}