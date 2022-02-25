package xyz.cryptohows.backend.upload.application.excel;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.exception.CryptoHowsException;

import java.io.IOException;

public class ExcelFileConverter {

    public static final String XLSX_FORMAT = "xlsx";
    public static final String XLS_FORMAT = "xls";

    private ExcelFileConverter() {
    }

    public static Workbook toWorkbook(MultipartFile file) {
        String extension = FilenameUtils.getExtension(file.getOriginalFilename());
        try {
            if (XLSX_FORMAT.equalsIgnoreCase(extension)) {
                return new XSSFWorkbook(file.getInputStream());
            } else if (XLS_FORMAT.equalsIgnoreCase(extension)) {
                return new HSSFWorkbook(file.getInputStream());
            } else {
                throw new CryptoHowsException("엑셀 파일만 업로드 가능합니다.");
            }
        } catch (IOException e) {
            throw new IllegalStateException("파일 읽기에 실패하였습니다.");
        }
    }
}