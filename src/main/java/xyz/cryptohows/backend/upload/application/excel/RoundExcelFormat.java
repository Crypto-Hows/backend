package xyz.cryptohows.backend.upload.application.excel;

import lombok.Getter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import xyz.cryptohows.backend.project.domain.Project;
import xyz.cryptohows.backend.round.domain.FundingStage;
import xyz.cryptohows.backend.round.domain.Round;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.cryptohows.backend.upload.application.excel.ExcelFileUtil.checkNullAndGetDoubleCellValue;
import static xyz.cryptohows.backend.upload.application.excel.ExcelFileUtil.checkNullAndGetStringCellValue;

@Getter
public class RoundExcelFormat {

    private final String projectName;
    private final String announcedDate;
    private final String moneyRaised;
    private final String fundingStage;
    private final String newsArticle;
    private final List<String> participants;

    public RoundExcelFormat(String projectName, String announcedDate, String moneyRaised, String fundingStage,
                            String newsArticle, List<String> participants) {
        this.projectName = projectName;
        this.announcedDate = announcedDate;
        this.moneyRaised = moneyRaised;
        this.fundingStage = fundingStage;
        this.newsArticle = newsArticle;
        this.participants = participants;
    }

    public static List<RoundExcelFormat> toList(MultipartFile file) {
        List<RoundExcelFormat> roundExcelFormats = new ArrayList<>();

        Workbook workbook = ExcelFileUtil.toWorkbook(file);
        Sheet excelSheet = workbook.getSheetAt(0);
        for (int i = 1; i < excelSheet.getPhysicalNumberOfRows(); i++) {
            Row row = excelSheet.getRow(i);
            RoundExcelFormat roundExcelFormat = new RoundExcelFormat(
                    row.getCell(0).getStringCellValue(),
                    checkNullAndGetStringCellValue(row.getCell(1)),
                    toDollar(checkNullAndGetDoubleCellValue(row.getCell(2))),
                    checkNullAndGetStringCellValue(row.getCell(3)),
                    checkNullAndGetStringCellValue(row.getCell(4)),
                    Arrays.asList(row.getCell(5).getStringCellValue().split(", "))
            );
            roundExcelFormats.add(roundExcelFormat);
        }

        return roundExcelFormats;
    }

    private static String toDollar(double value) {
        if (value == 0) {
            return "";
        }
        return "$" + NumberFormat.getInstance().format(value);
    }

    public Round toRound(Project project) {
        return Round.builder()
                .project(project)
                .announcedDate(announcedDate)
                .moneyRaised(moneyRaised)
                .fundingStage(FundingStage.of(fundingStage))
                .newsArticle(newsArticle)
                .build();
    }
}