package xyz.spc.serve.cluster.func.managers;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.infra.special.Cluster.managers.ClusterAuthsRepo;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClusterAuthFunc {

    // Repo
     private final ClusterAuthsRepo clusterAuthsRepo;

}
