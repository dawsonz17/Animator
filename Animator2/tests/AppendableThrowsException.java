import java.io.IOException;

/**
 * This class is used for testing whether our Animation Views can handle if my Appendable throws an
 * IO Exception.
 */
public class AppendableThrowsException implements Appendable {
  @Override
  public Appendable append(CharSequence csq) throws IOException {
    throw new IOException("Testing Appendable IO Exception");
  }

  @Override
  public Appendable append(CharSequence csq, int start, int end) throws IOException {
    throw new IOException("Testing Appendable IO Exception");
  }

  @Override
  public Appendable append(char c) throws IOException {
    throw new IOException("Testing Appendable IO Exception");
  }
}
