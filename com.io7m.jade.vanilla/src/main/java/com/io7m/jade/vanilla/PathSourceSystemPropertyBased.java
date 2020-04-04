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

import java.nio.file.Path;
import java.util.Objects;
import java.util.Optional;

final class PathSourceSystemPropertyBased implements PathSourceType
{
  private final Logger logger;
  private final String category;
  private final String variableName;
  private final PathTransformerType transform;

  PathSourceSystemPropertyBased(
    final Logger inLogger,
    final String inCategory,
    final String inVariableName,
    final PathTransformerType inTransform)
  {
    this.logger =
      Objects.requireNonNull(inLogger, "logger");
    this.category =
      Objects.requireNonNull(inCategory, "category");
    this.variableName =
      Objects.requireNonNull(inVariableName, "variableName");
    this.transform =
      Objects.requireNonNull(inTransform, "transform");
  }

  @Override
  public Optional<Path> tryPath(
    final ApplicationProviderContextType configuration,
    final ApplicationEnvironmentType environment)
  {
    final var filesystem = environment.filesystem();
    final var envOpt = environment.systemProperty(this.variableName);

    this.logger.debug("{}: system {}", this.category, this.variableName);
    if (envOpt.isPresent()) {
      final var env = envOpt.get();
      final var path = filesystem.getPath(env).toAbsolutePath();
      this.logger.debug(
        "{}: system {}: {}", this.category, this.variableName, path);
      return Optional.of(path)
        .map(p -> this.transform.transform(configuration, environment, p));
    }

    this.logger.debug(
      "{}: system {} not present", this.category, this.variableName);
    return Optional.empty();
  }
}
