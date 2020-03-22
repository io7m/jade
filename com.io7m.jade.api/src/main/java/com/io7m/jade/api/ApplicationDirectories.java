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

package com.io7m.jade.api;

import com.io7m.jade.spi.ApplicationDirectoryConfiguration;
import com.io7m.jade.spi.ApplicationDirectoryProviderType;
import com.io7m.jade.spi.ApplicationEnvironmentType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;
import java.util.ServiceLoader;

/**
 * The primary API used to get access to application directories.
 */

public final class ApplicationDirectories implements ApplicationDirectoriesType
{
  private static final Logger LOG =
    LoggerFactory.getLogger(ApplicationDirectories.class);

  private final ApplicationDirectoryProviderType provider;

  private ApplicationDirectories(
    final ApplicationDirectoryProviderType inProvider)
  {
    this.provider = Objects.requireNonNull(inProvider, "provider");
  }

  /**
   * Retrieve the application directories.
   *
   * @param configuration The application configuration
   *
   * @return The application directories
   */

  public static ApplicationDirectoriesType get(
    final ApplicationDirectoryConfiguration configuration)
  {
    return get(configuration, new RealEnvironment());
  }

  /**
   * Retrieve the application directories. This method allows for providing
   * a custom application environment and is primarily useful for testing
   * purposes.
   *
   * @param environment   The application environment
   * @param configuration The application configuration
   *
   * @return The application directories
   */

  public static ApplicationDirectoriesType get(
    final ApplicationDirectoryConfiguration configuration,
    final ApplicationEnvironmentType environment)
  {
    Objects.requireNonNull(configuration, "configuration");
    return createForConfiguration(configuration, environment);
  }

  private static ApplicationDirectoriesType createForConfiguration(
    final ApplicationDirectoryConfiguration configuration,
    final ApplicationEnvironmentType environment)
  {
    if (!isPortable(configuration, environment)) {
      final var iterator =
        ServiceLoader.load(ApplicationDirectoryProviderType.class)
          .iterator();

      while (iterator.hasNext()) {
        final var provider = iterator.next();
        final var matches = provider.initialize(configuration, environment);
        if (matches) {
          return new ApplicationDirectories(provider);
        }
      }
    }

    final var fallback = new PortableDirectories();
    fallback.initialize(configuration, environment);
    return new ApplicationDirectories(fallback);
  }

  private static boolean isPortable(
    final ApplicationDirectoryConfiguration configuration,
    final ApplicationEnvironmentType environment)
  {
    final var propNameOpt = configuration.portablePropertyName();
    if (propNameOpt.isPresent()) {
      final var propName = propNameOpt.get();
      LOG.debug("isPortable: checking system property {}", propName);
      final boolean portable =
        environment.systemProperty(propName)
          .or(() -> Optional.of("false"))
          .map(Boolean::valueOf)
          .orElse(Boolean.FALSE)
          .booleanValue();
      LOG.debug("isPortable: {}", Boolean.valueOf(portable));
      return portable;
    }

    LOG.debug("isPortable: {}", Boolean.FALSE);
    return false;
  }

  @Override
  public Path configurationDirectory()
  {
    return this.provider.configurationDirectory();
  }

  @Override
  public Path dataDirectory()
  {
    return this.provider.dataDirectory();
  }

  @Override
  public Path cacheDirectory()
  {
    return this.provider.cacheDirectory();
  }
}
