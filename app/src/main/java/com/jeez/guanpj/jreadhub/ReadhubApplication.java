package com.jeez.guanpj.jreadhub;

import com.tencent.tinker.loader.app.TinkerApplication;
import com.tencent.tinker.loader.shareutil.ShareConstants;

public class ReadhubApplication extends TinkerApplication {
    public ReadhubApplication() {
        super(ShareConstants.TINKER_ENABLE_ALL, "com.jeez.guanpj.jreadhub.ReadhubApplicationLike",
                "com.tencent.tinker.loader.TinkerLoader", false);
    }
}
