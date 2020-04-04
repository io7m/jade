/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> http://io7m.com
 *
 * Permission to use, copy, modify, and/or distribute this software for any
 * purpose with or without fee is hereby granted, provided that the above
 * copyright notice and this permission notice appear in all copies.
 *
 * THE SOFTWARE IS PROVIDED "AS IS" AND THE AUTHOR DISCLAIMS ALL WARRANTIES
 * WITH REGARD TO THIS SOFTWARE INCLUDING ALL IMPLIED WARRANTIES OF
 * MERCHANTABILITY AND FITNESS. IN NO EVENT SHALL THE AUTHOR BE LIABLE FOR ANY
 * SPECIAL, DIRECT, INDIRECT, OR CONSEQUENTIAL DAMAGES OR ANY DAMAGES
 * WHATSOEVER RESULTING FROM LOSS OF USE, DATA OR PROFITS, WHETHER IN AN
 * ACTION OF CONTRACT, NEGLIGENCE OR OTHER TORTIOUS ACTION, ARISING OUT OF OR
 * IN CONNECTION WITH THE USE OR PERFORMANCE OF THIS SOFTWARE.
 */

package com.io7m.jade.tests;

import com.io7m.jade.api.ApplicationDirectories;
import com.io7m.jade.api.ApplicationDirectoryConfiguration;
import com.io7m.jade.spi.ApplicationEnvironmentType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public final class ApplicationDirectoriesTest
{
  private static final Logger LOG =
    LoggerFactory.getLogger(ApplicationDirectoriesTest.class);

  @Test
  public void testIntegration()
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var directories = ApplicationDirectories.get(configuration);
    LOG.debug("config: {}", directories.configurationDirectory());
    LOG.debug("data:   {}", directories.dataDirectory());
  }

  @Test
  public void testIntegrationPortable()
  {
    System.setProperty(
      "com.io7m.jade.portable",
      "true"
    );

    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .setPortablePropertyName("com.io7m.jade.portable")
        .build();

    final var directories = ApplicationDirectories.get(configuration);
    LOG.debug("config: {}", directories.configurationDirectory());
    LOG.debug("data:   {}", directories.dataDirectory());

    Assertions.assertEquals(
      Paths.get("Widget", "config").toAbsolutePath(),
      directories.configurationDirectory()
    );
    Assertions.assertEquals(
      Paths.get("Widget", "data").toAbsolutePath(),
      directories.dataDirectory()
    );
    Assertions.assertEquals(
      Paths.get("Widget", "cache").toAbsolutePath(),
      directories.cacheDirectory()
    );
  }

  @Test
  public void testIntegrationPortableFalse()
  {
    System.setProperty(
      "com.io7m.jade.portable",
      "false"
    );

    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .setPortablePropertyName("com.io7m.jade.portable")
        .build();

    final var directories = ApplicationDirectories.get(configuration);
    LOG.debug("config: {}", directories.configurationDirectory());
    LOG.debug("data:   {}", directories.dataDirectory());
  }

  @Test
  public void testIntegrationPortableNotPresent()
  {
    System.clearProperty(
      "com.io7m.jade.portable"
    );

    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .setPortablePropertyName("com.io7m.jade.portable")
        .build();

    final var directories = ApplicationDirectories.get(configuration);
    LOG.debug("config: {}", directories.configurationDirectory());
    LOG.debug("data:   {}", directories.dataDirectory());
  }

  @Test
  public void testIntegrationNoServicesPortable()
  {
    final var environment =
      Mockito.mock(ApplicationEnvironmentType.class);

    Mockito.when(environment.systemProperty(Mockito.anyString()))
      .thenReturn(Optional.empty());
    Mockito.when(environment.environmentVariable(Mockito.anyString()))
      .thenReturn(Optional.empty());
    Mockito.when(environment.filesystem())
      .thenReturn(FileSystems.getDefault());
    Mockito.when(environment.servicesFor(Mockito.any()))
      .thenReturn(Collections.emptyIterator());

    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var directories =
      ApplicationDirectories.get(configuration, environment);

    Assertions.assertEquals(
      Paths.get("Widget", "config").toAbsolutePath(),
      directories.configurationDirectory()
    );
    Assertions.assertEquals(
      Paths.get("Widget", "data").toAbsolutePath(),
      directories.dataDirectory()
    );
    Assertions.assertEquals(
      Paths.get("Widget", "cache").toAbsolutePath(),
      directories.cacheDirectory()
    );
  }

  @Test
  public void testIntegrationOverride()
  {
    System.setProperty(
      "com.io7m.jade.override",
      "/tmp/x"
    );

    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .setOverridePropertyName("com.io7m.jade.override")
        .build();

    final var directories = ApplicationDirectories.get(configuration);
    LOG.debug("config: {}", directories.configurationDirectory());
    LOG.debug("data:   {}", directories.dataDirectory());

    Assertions.assertEquals(
      Paths.get("/tmp/x/config"),
      directories.configurationDirectory()
    );
    Assertions.assertEquals(
      Paths.get("/tmp/x/data"),
      directories.dataDirectory()
    );
    Assertions.assertEquals(
      Paths.get("/tmp/x/cache"),
      directories.cacheDirectory()
    );
  }
}
