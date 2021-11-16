package se.bjurr.violations.lib;

import static org.assertj.core.api.Assertions.assertThat;
import static se.bjurr.violations.lib.TestUtils.getRootFolder;
import static se.bjurr.violations.lib.ViolationsApi.violationsApi;
import static se.bjurr.violations.lib.model.SEVERITY.INFO;
import static se.bjurr.violations.lib.reports.Parser.SARIFPARSER;

import java.util.ArrayList;
import java.util.Set;

import org.junit.Test;

import se.bjurr.violations.lib.model.Violation;

public class SarifParserTest {

  @Test
  public void testThatViolationsCanBeParsed_I2() {
    final String rootFolder = getRootFolder();

    final Set<Violation> actual =
        violationsApi() //
            .withPattern(".*/sarif/example.3.9.5.json$") //
            .inFolder(rootFolder) //
            .findAll(SARIFPARSER) //
            .violations();

    assertThat(actual) //
        .hasSize(1);

    final Violation first = new ArrayList<>(actual).get(0);
    assertThat(first.getMessage()) //
        .isEqualTo("Return value of flagged function ignored - print");
    assertThat(first.getFile()) //
        .isEqualTo("perl/example.pl");
    assertThat(first.getSeverity()) //
        .isEqualTo(INFO);
    assertThat(first.getRule()) //
        .isEqualTo("See pages 208,278 of PBP.");
    assertThat(first.getStartLine()) //
        .isEqualTo(8);
    assertThat(first.getEndLine()) //
        .isEqualTo(8);
  }
}
