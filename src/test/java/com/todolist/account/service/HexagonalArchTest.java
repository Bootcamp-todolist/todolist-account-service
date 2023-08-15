package com.todolist.account.service;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.classes;
import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;
import static java.util.function.Predicate.not;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ImportOption;
import com.tngtech.archunit.junit.AnalyzeClasses;
import com.tngtech.archunit.junit.ArchTest;
import com.tngtech.archunit.lang.ArchRule;
import java.util.Arrays;
import java.util.stream.Stream;

@AnalyzeClasses(packages = "com.todolist.account.service", importOptions = ImportOption.DoNotIncludeTests.class)
public class HexagonalArchTest {

  @ArchTest
  static final ArchRule three_layer_application_rule =
      classes()
          .that().resideInAPackage("com.todolist.account.service..").and()
          .doNotBelongToAnyOf(TodolistAccountServiceApplication.class)
          .should().resideInAnyPackage(
              "..adapter..",
              "..advice..", "..application..", "..config..", "..security..",
              "..domain..", "..exception..", "..common..", "..utils..")
          .allowEmptyShould(true);

  @ArchTest
  static final ArchRule independence_of_domain_rule =
      noClasses()
          .that().resideInAPackage("..domain..")
          .should().dependOnClassesThat().resideInAnyPackage("..application..", "..adapter..")
          .allowEmptyShould(true);

  private static final String[] REGISTERED_ADAPTERS = Stream.of(
          "http", "mysql"
      )
      .map(pkg -> HexagonalArchTest.class.getPackageName() + ".adapter." + pkg + "..")
      .toArray(String[]::new);

  @ArchTest
  static final ArchRule all_adapter_should_be_registered =
      classes()
          .that().resideInAPackage("..adapter..")
          .should().resideInAnyPackage(REGISTERED_ADAPTERS);

  @ArchTest
  void segregation_of_interface_rule(JavaClasses classes) {
    Arrays.stream(REGISTERED_ADAPTERS).forEach(pkg -> {
      final var otherInterfaces = Arrays.stream(REGISTERED_ADAPTERS).filter(not(pkg::equals))
          .toArray(String[]::new);
      noClasses()
          .that().resideInAPackage(pkg)
          .should().dependOnClassesThat().resideInAnyPackage(otherInterfaces)
          .check(classes);
    });
  }

  @ArchTest
  static final ArchRule only_mysql_package_should_depend_on_jpa =
      noClasses()
          .that().resideOutsideOfPackages("..mysql..", "..config..")
          .should().dependOnClassesThat().resideInAnyPackage("org.springframework.data.jpa..")
          .allowEmptyShould(true);
}
