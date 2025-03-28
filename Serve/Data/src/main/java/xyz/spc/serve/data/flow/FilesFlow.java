package xyz.spc.serve.data.flow;


import jakarta.validation.constraints.NotNull;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import xyz.spc.serve.data.func.files.FilesFunc;

@Slf4j
@Service
@RequiredArgsConstructor
public class FilesFlow {


    //Feign


    //Func
    private final FilesFunc filesFunc;


    public void getUserDataOfFile(@NotNull Long id, Long current, Long size) {
        filesFunc.getUserDataOfFile(id, current, size);
    }
}
