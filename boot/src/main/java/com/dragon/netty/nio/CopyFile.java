package com.dragon.netty.nio;// $Id$

import java.io.*;
import java.nio.*;
import java.nio.channels.*;

public class CopyFile
{
  static public void main( String args[] ) throws Exception {

    FileInputStream fin1 = new FileInputStream( new File("D:\\tools\\系统镜像\\WIN7-64_2017V.GHO") );
    FileChannel channel = fin1.getChannel();
    System.out.println(channel.size());

    if (args.length<2) {
      System.err.println( "Usage: java CopyFile infile outfile" );
      System.exit( 1 );
    }

    String infile = args[0];
    String outfile = args[1];

    FileInputStream fin = new FileInputStream( infile );
    FileOutputStream fout = new FileOutputStream( outfile );

    FileChannel fcin = fin.getChannel();
    FileChannel fcout = fout.getChannel();

    ByteBuffer buffer = ByteBuffer.allocate( 1024 );

    while (true) {
      buffer.clear();

      int r = fcin.read( buffer );

      if (r==-1) {
        break;
      }

      buffer.flip();

      fcout.write( buffer );
    }
  }
}
