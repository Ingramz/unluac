package unluac.parse;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.ByteBuffer;

import unluac.Version;


public abstract class LConstantType extends BObjectType<LObject> {
  
  public static LConstantType get(Version version) {
    if(version.getVersionNumber() >= 0x54) {
      return getType54();
    } else if(version.getVersionNumber() >= 0x53) {
      return getType53();
    } else {
      return getType50();
    }
  }
  
  public static LConstantType50 getType50() {
    return new LConstantType50();
  }
  
  public static LConstantType53 getType53() {
    return new LConstantType53();
  }
  
  public static LConstantType54 getType54() {
    return new LConstantType54();
  }
  
}

class LConstantType50 extends LConstantType {

  @Override
  public LObject parse(ByteBuffer buffer, BHeader header) {
    int type = 0xFF & buffer.get();
    if(header.debug) {
      System.out.print("-- parsing <constant>, type is ");
      switch(type) {
        case 0:
          System.out.println("<nil>");
          break;
        case 1:
          System.out.println("<boolean>");
          break;
        case 3:
          System.out.println("<number>");
          break;
        case 4:
          System.out.println("<string>");
          break;
        default:
          System.out.println("illegal " + type);
          break;
      }
    }
    switch(type) {
      case 0:
        return LNil.NIL;
      case 1:
        return header.bool.parse(buffer, header);
      case 3:
        return header.number.parse(buffer, header);
      case 4:
        return header.string.parse(buffer, header);
      default:
        throw new IllegalStateException();
    }
  }
  
  @Override
  public void write(OutputStream out, BHeader header, LObject object) throws IOException {
    if(object instanceof LNil) {
      out.write(0);
    } else if(object instanceof LBoolean) {
      out.write(1);
      header.bool.write(out, header, (LBoolean)object);
    } else if(object instanceof LNumber) {
      out.write(3);
      header.number.write(out, header, (LNumber)object);
    } else if(object instanceof LString) {
      out.write(4);
      header.string.write(out, header, (LString)object);
    } else {
      throw new IllegalStateException();
    }
  }
  
}

class LConstantType53 extends LConstantType {

  @Override
  public LObject parse(ByteBuffer buffer, BHeader header) {
    int type = 0xFF & buffer.get();
    if(header.debug) {
      System.out.print("-- parsing <constant>, type is ");
      switch(type) {
        case 0:
          System.out.println("<nil>");
          break;
        case 1:
          System.out.println("<boolean>");
          break;
        case 3:
          System.out.println("<float>");
          break;
        case 0x13:
          System.out.println("<integer>");
          break;
        case 4:
          System.out.println("<short string>");
          break;
        case 0x14:
          System.out.println("<long string>");
          break;
        default:
          System.out.println("illegal " + type);
          break;
      }
    }
    switch(type) {
      case 0:
        return LNil.NIL;
      case 1:
        return header.bool.parse(buffer, header);
      case 3:
        return header.lfloat.parse(buffer, header);
      case 0x13:
        return header.linteger.parse(buffer, header);
      case 4:
      case 0x14:
        return header.string.parse(buffer, header);
      default:
        throw new IllegalStateException();
    }
  }
  
  @Override
  public void write(OutputStream out, BHeader header, LObject object) throws IOException {
    if(object instanceof LNil) {
      out.write(0);
    } else if(object instanceof LBoolean) {
      out.write(1);
      header.bool.write(out, header, (LBoolean)object);
    } else if(object instanceof LNumber) {
      LNumber n = (LNumber)object;
      if(!n.integralType()) {
        out.write(3);
        header.lfloat.write(out, header, (LNumber)object);
      } else {
        out.write(0x13);
        header.linteger.write(out, header, (LNumber)object);
      }
    } else if(object instanceof LString) {
      out.write(4);
      // TODO: long strings?
      header.string.write(out, header, (LString)object);
    } else {
      throw new IllegalStateException();
    }
  }
  
}

class LConstantType54 extends LConstantType {

  @Override
  public LObject parse(ByteBuffer buffer, BHeader header) {
    int type = 0xFF & buffer.get();
    switch(type) {
      case 0:
        return LNil.NIL;
      case 1:
        return LBoolean.LFALSE;
      case 0x11:
        return LBoolean.LTRUE;
      case 3:
        return header.linteger.parse(buffer, header);
      case 0x13:
        return header.lfloat.parse(buffer, header);
      case 4:
      case 0x14:
        return header.string.parse(buffer, header);
      default:
        throw new IllegalStateException();
    }
  }
  
  @Override
  public void write(OutputStream out, BHeader header, LObject object) throws IOException {
    if(object instanceof LNil) {
      out.write(0);
    } else if(object instanceof LBoolean) {
      if(((LBoolean) object).value()) {
        out.write(0x11);
      } else {
        out.write(1);
      }
    } else if(object instanceof LNumber) {
      LNumber n = (LNumber)object;
      if(!n.integralType()) {
        out.write(0x13);
        header.lfloat.write(out, header, (LNumber)object);
      } else {
        out.write(3);
        header.linteger.write(out, header, (LNumber)object);
      }
    } else if(object instanceof LString) {
      out.write(4);
      // TODO: long strings?
      header.string.write(out, header, (LString)object);
    } else {
      throw new IllegalStateException();
    }
  }
  
}
