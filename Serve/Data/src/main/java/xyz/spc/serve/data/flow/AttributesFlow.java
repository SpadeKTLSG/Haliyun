package xyz.spc.serve.data.flow;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.feign.Cluster.ClustersClient;
import xyz.spc.serve.data.func.attributes.FileTagFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttributesFlow {


    //Feign
    private final ClustersClient clustersClient;

    //Func
    private final FileTagFunc fileTagFunc;

}
