package com.gjn.easytool;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author gjn
 * @time 2019/4/11 16:57
 */

public interface IUrl {

    @GET("{count}/{page}")
    Observable<Pic> getPic(@Path("page") int page, @Path("count") int count);
}
