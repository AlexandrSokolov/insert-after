package com.savdev.insert_after;

import java.io.File;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;
import org.apache.commons.io.FileUtils;

public class AppService {

  public static final String PATH_OPTION = "path";
  public static final String INSERT_AFTER_OPTION = "insert-after";
  public static final String INSERT_TEXT = "insert-text";

  public static void main(String ...args) throws IOException {
    AppService service = new AppService();
    Optional<CommandLine> cmdMayby = service.parseCmd(args);
    if (cmdMayby.isPresent()) {
      CommandLine cmd = cmdMayby.get();
      String filePath = cmd.getOptionValue(PATH_OPTION);
      String replacedText = new InsertTextService()
        .insertText(
          filePath,
          cmd.getOptionValue(INSERT_AFTER_OPTION),
          cmd.getOptionValue(INSERT_TEXT));
      FileUtils.write(
        new File(filePath),
        replacedText,
        StandardCharsets.UTF_8.toString());
    }
  }

  private Optional<CommandLine> parseCmd(String ...args) {
    Options options = new Options();
    options.addOption(
      Option.builder()
            .argName("file path")
            .required()
            .hasArg()
            .longOpt(PATH_OPTION)
            .desc("path to the file to update")
            .build());
    options.addOption(
      Option.builder()
            .argName("block of text to search")
            .required()
            .hasArg()
            .longOpt(INSERT_AFTER_OPTION)
            .desc("block of text to search to insert after it")
            .build());
    options.addOption(
      Option.builder()
            .argName("block of text to insert")
            .required()
            .hasArg()
            .longOpt(INSERT_TEXT)
            .desc("block of text to insert")
            .build());

    CommandLineParser parser = new DefaultParser();
    try {
      // parse the command line arguments
      return Optional.of(
        parser.parse( options, args ));
    }
    catch( ParseException exp ) {
      // oops, something went wrong
      System.err.println( "Parsing failed.  Reason: " + exp.getMessage() );
      HelpFormatter formatter = new HelpFormatter();
      formatter.printHelp( currentFileName(), options );
      return Optional.empty();
    }
  }

  private String currentFileName() {
    try {
      return new File(AppService.class
        .getProtectionDomain()
        .getCodeSource()
        .getLocation()
        .toURI())
        .getName();
    } catch (URISyntaxException e) {
      throw new RuntimeException(e);
    }
  }
}
