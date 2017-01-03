package com.nk.flyboy.core.util;

import net.jpountz.lz4.*;
import org.anarres.lzo.*;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorInputStream;
import org.apache.commons.compress.compressors.bzip2.BZip2CompressorOutputStream;
import org.xerial.snappy.Snappy;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.zip.*;

/**
 * Created by kai on 2017/1/3.
 * deflate��gzip��bzip2����עѹ���ʣ�ѹ���ͽ�ѹ��ʱ��������
 * lzo��lz4�Լ�snappy��3��ѹ���㷨������ѹ���ٶ�Ϊ���ȣ�ѹ���ʻ���ѷһ�
 * lzo��lz4�Լ�snappy��cpu�߷����һ�㡣��Ϊ�����̵�ѹ����֮�ڣ����Ǹ��ӹ�עѹ���ͽ�ѹ��ʱ�䣬�Լ�cpuʹ�ã�
 * snappy��ѹ���ͽ�ѹ��ʱ���Լ�cpu�߷嶼����͵ģ�������ѹ������Ҳû��̫������ơ�
 */
public class Compress {

    /**
     * ����ѹ��
     */
    public byte[] deflateCompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        Deflater compressor=new Deflater(1);

        try{
            compressor.setInput(source);
            compressor.finish();
            byte[] buf=new byte[2048];
            while (!compressor.finished()){
                int count=compressor.deflate(buf);
                bos.write(buf,0,count);
            }

        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            compressor.end();
        }
        return bos.toByteArray();
    }

    public byte[] deflateUncompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        Inflater decompressor=new Inflater();

        try {
            decompressor.setInput(source);
            byte[] buf=new byte[2048];
            while (!decompressor.finished()){
                int count=decompressor.inflate(buf);
                bos.write(buf,0,count);
            }
        }catch (DataFormatException ex){
            ex.printStackTrace();
        }finally {
            decompressor.end();
        }

        return bos.toByteArray();
    }

    public byte[] gzipCompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        GZIPOutputStream gzip;
        try{
            gzip=new GZIPOutputStream(bos);
            gzip.write(source);
            gzip.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {

        }
        return bos.toByteArray();
    }

    public byte[] gzipUncompress(byte[] soure){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ByteArrayInputStream in=new ByteArrayInputStream(soure);
        try{
            GZIPInputStream ungzip=new GZIPInputStream(in);
            byte[] buffer=new byte[2048];
            int n;
            while ((n=ungzip.read(buffer))>=0){
                bos.write(buffer,0,n);
            }
            ungzip.close();
        }catch (Exception ex){

        }finally {

        }
        return bos.toByteArray();
    }

    public byte[] bzipCompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        try {
            BZip2CompressorOutputStream bcos=new BZip2CompressorOutputStream(bos);
            bcos.write(source);
            bcos.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return bos.toByteArray();
    }

    public byte[] bzipUncompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();
        ByteArrayInputStream in =new ByteArrayInputStream(source);

        try {
            BZip2CompressorInputStream unbzip=new BZip2CompressorInputStream(in);
            byte[] buf=new byte[2048];
            int n;
            while ((n=unbzip.read(buf))>=0){
                bos.write(buf,0,n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }


    /**
     * ����ѹ��
     */
    public byte[] lzoCompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        try {
            LzoCompressor compressor= LzoLibrary.getInstance().newCompressor(LzoAlgorithm.LZO1X,null);
            LzoOutputStream cs=new LzoOutputStream(bos,compressor);
            cs.write(source);
            cs.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    public byte[] lzoUncompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        try {
            LzoDecompressor decompressor=LzoLibrary.getInstance().newDecompressor(LzoAlgorithm.LZO1X, null);
            ByteArrayInputStream in=new ByteArrayInputStream(source);

            LzoInputStream us=new LzoInputStream(in,decompressor);
            int n;
            byte[] buf=new byte[2048];
            while ((n=us.read(buf))>=0){
                bos.write(buf,0,n);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        return  bos.toByteArray();
    }

    /**
     * ����ѹ��
     */
    public byte[] lz4Compress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        try{
            LZ4Factory factory=LZ4Factory.fastestInstance();
            LZ4Compressor compressor=factory.fastCompressor();
            LZ4BlockOutputStream lz4BlockOutputStream=new LZ4BlockOutputStream(bos,2048,compressor);
            lz4BlockOutputStream.write(source);
            lz4BlockOutputStream.close();
        }catch (Exception ex){}finally {

        }
        return bos.toByteArray();
    }

    public byte[] lz4Uncompress(byte[] source){
        ByteArrayOutputStream bos=new ByteArrayOutputStream();

        try{
            LZ4Factory factory=LZ4Factory.fastestInstance();
            LZ4FastDecompressor lz4FastDecompressor=factory.fastDecompressor();
            LZ4BlockInputStream inputStream=new LZ4BlockInputStream(new ByteArrayInputStream(source),lz4FastDecompressor);
            int n;
            byte[] buf=new byte[2048];
            while ((n=inputStream.read(buf))>=0){
                bos.write(buf,0,n);
            }
        }catch (Exception ex){}finally {

        }
        return bos.toByteArray();
    }

    public byte[] snappyCompress(byte[] source){
        try {
            return Snappy.compress(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public byte[] snappyUncompress(byte[] source){
        try {
            return Snappy.uncompress(source);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}
