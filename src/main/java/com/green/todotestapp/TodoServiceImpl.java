package com.green.todotestapp;


import com.green.todotestapp.model.TodoInsDto;
import com.green.todotestapp.model.TodoInsParam;
import com.green.todotestapp.model.TodoRes;
import com.green.todotestapp.model.TodoVo;
import com.green.todotestapp.utils.MyFileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Slf4j
@Service
public class TodoServiceImpl implements TodoService {

    private final TodoMapper MAPPER;
    private final String FILE_DIR; //절대 경로

    @Autowired
    public TodoServiceImpl(TodoMapper MAPPER, @Value("${file.dir}") String fileDir) {
        this.MAPPER = MAPPER;
        this.FILE_DIR = MyFileUtils.getAbsolutePath(fileDir);;
    }

    @Override
    public TodoRes insTodo(TodoInsParam p) {
        File tempDic = new File(FILE_DIR, "/temp");
        //File tempDic = new File(FILE_DIR + "/temp"); //위와 동일한 결과
        if(!tempDic.exists()) {
            tempDic.mkdirs();
        }
        //저장용 파일명
        String savedFileNm = MyFileUtils.makeRandomFileNm(p.getPic().getOriginalFilename());

        File tempFile = new File(tempDic.getPath(), savedFileNm);
        try {
            p.getPic().transferTo(tempFile);
        } catch (IOException e) {
            e.printStackTrace();
        }

        TodoInsDto dto = new TodoInsDto();
        dto.setCtnt(p.getCtnt());
        dto.setPic(savedFileNm);

        int result = MAPPER.insTodo(dto);
        if(result == 1) {
            //파일 이동
            String targetDicPath = FILE_DIR + "/todo/" + dto.getItodo();
            File targetDic = new File(targetDicPath);
            if(!targetDic.exists()) {
                targetDic.mkdirs();
            }
            File targetFile = new File(targetDicPath, savedFileNm);
            tempFile.renameTo(targetFile);
            return new TodoRes(dto);
        }
        return null;
    }

    @Override
    public List<TodoVo> selTodo() {
        return MAPPER.selTodo();
    }

}
