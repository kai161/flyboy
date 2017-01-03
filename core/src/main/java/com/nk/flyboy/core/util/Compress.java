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
 * deflate、gzip和bzip2更关注压缩率，压缩和解压缩时间会更长；
 * lzo，lz4以及snappy这3中压缩算法，均已压缩速度为优先，压缩率会稍逊一筹；
 * lzo，lz4以及snappy在cpu高峰更低一点。因为在容忍的压缩率之内，我们更加关注压缩和解压缩时间，以及cpu使用，
 * snappy在压缩和解压缩时间以及cpu高峰都是最低的，并且在压力率上也没有太多的劣势。
 */
public class Compress {

    /**
     * 无损压缩
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
     * 无损压缩
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
     * 无损压缩
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
