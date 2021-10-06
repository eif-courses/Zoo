package eif.viko.lt.zoo;

import retrofit2.Call;
import retrofit2.http.GET;

import java.util.List;

public interface GyvunasAPI {

    @GET("gyvunai.json")
    Call<List<Gyvunas>> getGyvunai();

}
