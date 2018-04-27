import java.io.IOException;

import org.apache.hadoop.conf.Configuration;

import com.alonzo.transformer.model.dim.base.PlatformDimension;
import com.alonzo.transformer.service.rpc.IDimensionConverter;
import com.alonzo.transformer.service.rpc.client.DimensionConverterClient;
import com.alonzo.transformer.service.rpc.server.DimensionConverterServer;

public class TestRPC {
    public static void main(String[] args) throws IOException {
        //DimensionConverterServer.main(args);

        IDimensionConverter converter = DimensionConverterClient.createDimensionConverter(new Configuration());
        System.out.println(converter);
        System.out.println(converter.getDimensionIdByValue(new PlatformDimension("test")));
        DimensionConverterClient.stopDimensionConverterProxy(converter);
    }
}
