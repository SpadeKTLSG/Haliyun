import org.junit.jupiter.api.Test;
import xyz.spc.common.dbutil.HDFSUtil1;

/**
 * 评价是还欠点火候
 */
public class HDFSUtilsTest1 {


    //listRemoteFiles
    @Test
    public void testListRemoteFiles() {
        try {
            System.out.println(HDFSUtil1.listRemoteFiles("/TEST"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //listRemoteDir
    @Test
    public void testListRemoteDirs() {
        try {
            System.out.println(HDFSUtil1.listRemoteDir("/TEST"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
