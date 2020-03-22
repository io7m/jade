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

import com.io7m.jade.spi.ApplicationDirectoryConfiguration;
import com.io7m.jade.spi.ApplicationEnvironmentType;
import com.io7m.jade.vanilla.ApplicationDirectoriesWindows;
import com.io7m.jade.vanilla.SystemSelection;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.nio.file.FileSystems;
import java.nio.file.Paths;
import java.util.Optional;

public final class ApplicationDirectoriesWindowsTest
{
  private ApplicationEnvironmentType environment;

  @BeforeEach
  public void testSetup()
  {
    this.environment =
      Mockito.mock(ApplicationEnvironmentType.class);
  }

  @Test
  public void testWrongOS()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var directories = new ApplicationDirectoriesWindows();
    directories.setSystemSelection(
      new SystemSelection()
        .setUnix(true)
        .setWindows(false)
    );

    final var initialized =
      directories.initialize(configuration, this.environment);
    Assertions.assertFalse(initialized);
  }

  @Test
  public void testInitialize()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var directories = new ApplicationDirectoriesWindows();
    directories.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    Mockito.when(this.environment.filesystem())
      .thenReturn(FileSystems.getDefault());
    Mockito.when(this.environment.environmentVariable("APPDATA"))
      .thenReturn(Optional.of("C:\\Users\\Grouch"));

    final var initialized =
      directories.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);
  }

  @Test
  public void testInitializeConfiguration0()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("APPDATA"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .resolve("AppData")
          .resolve("Roaming")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("AppData")
        .resolve("Roaming")
        .resolve("Widget")
        .resolve("config"),
      dir.configurationDirectory());
  }

  @Test
  public void testInitializeConfiguration1()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.systemProperty("user.home"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("config"),
      dir.configurationDirectory());
  }

  @Test
  public void testInitializeConfiguration2()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      Paths.get("")
        .resolve("Widget")
        .resolve("config")
        .toAbsolutePath(),
      dir.configurationDirectory());
  }

  @Test
  public void testInitializeConfiguration3()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("HOME"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("config"),
      dir.configurationDirectory());
  }

  @Test
  public void testInitializeData0()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("APPDATA"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .resolve("AppData")
          .resolve("Roaming")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("AppData")
        .resolve("Roaming")
        .resolve("Widget")
        .resolve("data"),
      dir.dataDirectory());
  }

  @Test
  public void testInitializeData1()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.systemProperty("user.home"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("data"),
      dir.dataDirectory());
  }

  @Test
  public void testInitializeData2()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    Mockito.when(this.environment.filesystem())
      .thenReturn(FileSystems.getDefault());

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      Paths.get("")
        .resolve("Widget")
        .resolve("data")
        .toAbsolutePath(),
      dir.dataDirectory());
  }

  @Test
  public void testInitializeData3()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("HOME"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("data"),
      dir.dataDirectory());
  }

  @Test
  public void testInitializeCache0()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("LOCALAPPDATA"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .resolve("AppCache")
          .resolve("Local")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("AppCache")
        .resolve("Local")
        .resolve("Widget"),
      dir.cacheDirectory());
  }

  @Test
  public void testInitializeCache1()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.systemProperty("user.home"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("cache"),
      dir.cacheDirectory());
  }

  @Test
  public void testInitializeCache2()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    Mockito.when(this.environment.filesystem())
      .thenReturn(FileSystems.getDefault());

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      Paths.get("")
        .resolve("Widget")
        .resolve("cache")
        .toAbsolutePath(),
      dir.cacheDirectory());
  }

  @Test
  public void testInitializeCache3()
    throws Exception
  {
    final var configuration =
      ApplicationDirectoryConfiguration.builder()
        .setApplicationName("Widget")
        .build();

    final var dir = new ApplicationDirectoriesWindows();
    dir.setSystemSelection(
      new SystemSelection()
        .setUnix(false)
        .setWindows(true)
    );

    final var filesystem = FileSystems.getDefault();
    final var root = filesystem.getRootDirectories().iterator().next();

    Mockito.when(this.environment.filesystem())
      .thenReturn(filesystem);
    Mockito.when(this.environment.environmentVariable("HOME"))
      .thenReturn(Optional.of(
        root.resolve("Users")
          .resolve("Grouch")
          .toString()
      ));

    final var initialized = dir.initialize(configuration, this.environment);
    Assertions.assertTrue(initialized);

    Assertions.assertEquals(
      root.resolve("Users")
        .resolve("Grouch")
        .resolve("Widget")
        .resolve("cache"),
      dir.cacheDirectory());
  }
}
