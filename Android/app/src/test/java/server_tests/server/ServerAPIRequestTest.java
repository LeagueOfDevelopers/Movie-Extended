package server_tests.server;

import com.lod.movie_extended.BuildConfig;
import com.lod.movie_extended.data.remote.ServerAPI;
import com.lod.movie_extended.util.DefaultConfig;
import com.lod.movie_extended.util.RxSchedulersOverrideRule;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.robolectric.RobolectricGradleTestRunner;
import org.robolectric.annotation.Config;

/**
 * To work on unit tests, switch the Test Artifact in the Build Variants view.
 */

@RunWith(RobolectricGradleTestRunner.class)
@Config(constants = BuildConfig.class, sdk = DefaultConfig.EMULATE_SDK)
public class ServerAPIRequestTest {

    private ServerAPI serverAPI;

    @Before
    public void setUp() {
        serverAPI = ServerAPI.Creator.newService();
    }

    @Rule
    public final RxSchedulersOverrideRule mOverrideSchedulersRule = new RxSchedulersOverrideRule();

    @Test
    public void getToken_MustNotBeNullOrEmpty() throws Exception {
//        String qrCode = "00000000-0000-0000-0000-000000000000";
//        serverAPI.getSession(qrCode)
//                .subscribeOn(Schedulers.immediate())
//                .subscribe(new Subscriber<String>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//                        throw new RuntimeException("");
//                    }
//
//                    @Override
//                    public void onNext(String s) {
//                        assertNotNull(s);
//                        assertNotEquals(s,"");
//                    }
//                });
    }
}