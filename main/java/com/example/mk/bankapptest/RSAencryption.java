package com.example.mk.bankapptest;

import java.math.BigInteger;
import java.util.Random;

public class RSAencryption {
    private BigInteger p;
    private BigInteger q;
    private BigInteger N;
    private BigInteger phi;
    private BigInteger e;
    private BigInteger d;
    private int        bitlength = 1024;
    private Random     r;


    public RSAencryption() {
        r = new Random();
        p = BigInteger.probablePrime(bitlength, r);
        q = BigInteger.probablePrime(bitlength, r);
        N = p.multiply(q);
        phi = p.subtract(BigInteger.ONE).multiply(q.subtract(BigInteger.ONE));
        e = BigInteger.probablePrime(bitlength / 2, r);
        while (phi.gcd(e).compareTo(BigInteger.ONE) > 0 && e.compareTo(phi) < 0)
        {
            e.add(BigInteger.ONE);
        }
        d = e.modInverse(phi);
        // TODO Auto-generated constructor stub
    }



    public byte[] encrpytPassword(String s){
        System.out.println("encrypting password");
        byte[] encrypted = encrypt(s.getBytes());
        return encrypted;
    }


    public String decryptPassword(byte[] password){
        System.out.println("Decrypting password");
        byte[] decrypted = decrypt(password);
        String decryptedPassword = new String(decrypted);
        System.out.println(decryptedPassword);
        return decryptedPassword;

    }


    private static String bytesToString(byte[] encrypted)
    {
        String test = "";
        for (byte b : encrypted)
        {
            test += Byte.toString(b);
        }
        return test;
    }


    public byte[] encrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(e, N).toByteArray();
    }

    // Decrypt message
    public byte[] decrypt(byte[] message)
    {
        return (new BigInteger(message)).modPow(d, N).toByteArray();
    }



}
