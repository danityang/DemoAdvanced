package com.cdemo.threelevelcache.dispatch;

import okhttp3.ResponseBody;
import retrofit2.http.GET;
import rx.Observable;

/**
 * Created by yangdi on 2017/8/9.
 */

public interface HttpAgentService {

    @GET
    Observable<ResponseBody> getStream();

}
