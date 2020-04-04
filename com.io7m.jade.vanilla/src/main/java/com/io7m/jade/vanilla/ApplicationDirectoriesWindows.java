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

package com.io7m.jade.vanilla;

import com.io7m.jade.spi.ApplicationEnvironmentType;
import com.io7m.jade.spi.ApplicationProviderContextType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.List;
import java.util.Objects;

public final class ApplicationDirectoriesWindows extends AbstractDirectories
{
  private static final Logger LOG =
    LoggerFactory.getLogger(ApplicationDirectoriesWindows.class);

  private static final PathSourceType CONFIG_DIR_ENV_APPDATA =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "configurationDirectory",
      "APPDATA",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("config")
    );

  private static final PathSourceType CONFIG_DIR_SYSTEM_USER_HOME =
    new PathSourceSystemPropertyBased(
      LOG,
      "configurationDirectory",
      "user.home",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("config")
          .toAbsolutePath()
    );

  private static final PathSourceType CONFIG_DIR_ENV_HOME =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "configurationDirectory",
      "HOME",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("config")
          .toAbsolutePath()
    );

  private static final PathSourceType DATA_DIR_ENV_APPDATA =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "dataDirectory",
      "APPDATA",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("data")
          .toAbsolutePath()
    );

  private static final PathSourceType DATA_DIR_SYSTEM_USER_HOME =
    new PathSourceSystemPropertyBased(
      LOG,
      "dataDirectory",
      "user.home",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("data")
          .toAbsolutePath()
    );

  private static final PathSourceType DATA_DIR_ENV_HOME =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "dataDirectory",
      "HOME",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("data")
          .toAbsolutePath()
    );

  private static final PathSourceType CACHE_DIR_ENV_LOCALAPPDATA =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "cacheDirectory",
      "LOCALAPPDATA",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .toAbsolutePath()
    );

  private static final PathSourceType CACHE_DIR_SYSTEM_USER_HOME =
    new PathSourceSystemPropertyBased(
      LOG,
      "cacheDirectory",
      "user.home",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("cache")
          .toAbsolutePath()
    );

  private static final PathSourceType CACHE_DIR_ENV_HOME =
    new PathSourceEnvironmentVariableBased(
      LOG,
      "cacheDirectory",
      "HOME",
      (configuration, environment, path) ->
        path.resolve(configuration.applicationName())
          .resolve("cache")
          .toAbsolutePath()
    );

  private static final List<PathSourceType> CONFIG_DIRECTORY_SOURCES =
    List.of(
      CONFIG_DIR_ENV_APPDATA,
      CONFIG_DIR_SYSTEM_USER_HOME,
      CONFIG_DIR_ENV_HOME
    );

  private static final List<PathSourceType> DATA_DIRECTORY_SOURCES =
    List.of(
      DATA_DIR_ENV_APPDATA,
      DATA_DIR_SYSTEM_USER_HOME,
      DATA_DIR_ENV_HOME
    );

  private static final List<PathSourceType> CACHE_DIRECTORY_SOURCES =
    List.of(
      CACHE_DIR_ENV_LOCALAPPDATA,
      CACHE_DIR_SYSTEM_USER_HOME,
      CACHE_DIR_ENV_HOME
    );

  private Path configurationDirectory;
  private Path dataDirectory;
  private Path cacheDirectory;

  public ApplicationDirectoriesWindows()
  {

  }

  private static Path makeConfigDirectory(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    for (final var source : CONFIG_DIRECTORY_SOURCES) {
      final var result = source.tryPath(configuration, environment);
      if (result.isPresent()) {
        return result.get();
      }
    }

    final Path fallback =
      environment.filesystem()
        .getPath("")
        .resolve(configuration.applicationName())
        .resolve("config")
        .toAbsolutePath();

    LOG.debug("configurationDirectory: used fallback: {}", fallback);
    return fallback;
  }

  private static Path makeDataDirectory(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    for (final var source : DATA_DIRECTORY_SOURCES) {
      final var result = source.tryPath(configuration, environment);
      if (result.isPresent()) {
        return result.get();
      }
    }

    final Path fallback =
      environment.filesystem()
        .getPath("")
        .resolve(configuration.applicationName())
        .resolve("data")
        .toAbsolutePath();

    LOG.debug("dataDirectory: used fallback: {}", fallback);
    return fallback;
  }

  private static Path makeCacheDirectory(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    for (final var source : CACHE_DIRECTORY_SOURCES) {
      final var result = source.tryPath(configuration, environment);
      if (result.isPresent()) {
        return result.get();
      }
    }

    final Path fallback =
      environment.filesystem()
        .getPath("")
        .resolve(configuration.applicationName())
        .resolve("cache")
        .toAbsolutePath();

    LOG.debug("cacheDirectory: used fallback: {}", fallback);
    return fallback;
  }

  @Override
  public boolean initialize(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    Objects.requireNonNull(configuration, "configuration");
    Objects.requireNonNull(environment, "environment");

    if (!this.systemSelection().isWindows()) {
      LOG.debug("not a Windows platform");
      return false;
    }

    this.configurationDirectory =
      makeConfigDirectory(configuration, environment);
    this.dataDirectory =
      makeDataDirectory(configuration, environment);
    this.cacheDirectory =
      makeCacheDirectory(configuration, environment);

    return true;
  }

  @Override
  public Path configurationDirectory()
  {
    return this.configurationDirectory;
  }

  @Override
  public Path dataDirectory()
  {
    return this.dataDirectory;
  }

  @Override
  public Path cacheDirectory()
  {
    return this.cacheDirectory;
  }
}
