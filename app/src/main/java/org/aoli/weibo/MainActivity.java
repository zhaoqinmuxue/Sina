package org.aoli.weibo;

import org.aoli.weibo.delegates.BaseDelegate;
import org.aoli.weibo.delegates.main.MainDelegate;

public class MainActivity extends BaseActivity {

    @Override
    public BaseDelegate setRootDelegate() {
        return new MainDelegate();
    }
}
