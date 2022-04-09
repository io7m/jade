/*
 * Copyright © 2020 Mark Raynsford <code@io7m.com> https://www.io7m.com
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

import com.io7m.immutables.styles.ImmutablesStyleType;
import org.immutables.value.Value;

import java.util.Optional;

/**
 * The type of application directory configurations.
 */

@ImmutablesStyleType
@Value.Immutable
public interface ApplicationDirectoryConfigurationType
{
  /**
   * The name of the application. The name is used in paths and must be
   * valid according to {@link ApplicationNames#isValid(String)}.
   *
   * @return The name of the application.
   */

  String applicationName();

  /**
   * The name of a system property which, if present, will be used to enable <i>portable</i>
   * mode. If the system property is present, and can be parsed as a {@code true}
   * value, then <i>portable</i> mode will be enabled and all calculated
   * application paths will be relative to the application's working directory.
   *
   * @return A property name to enable portable mode
   */

  Optional<String> portablePropertyName();

  /**
   * The name of a system property which, if present, will be used to specify
   * a fixed base directory against which all other application directories will
   * be resolved. If the system property is present, the contents will be
   * used directory as the name of a base directory, and all calculated application
   * paths will be relative to this directory.
   *
   * Paths exactly equivalent to those produced by <i>portable</i> mode can
   * be obtained by specifying the application's working directory in the
   * system property.
   *
   * @return A property name to enable portable mode
   */

  Optional<String> overridePropertyName();

  /**
   * Check preconditions for the type.
   */

  @Value.Check
  default void checkPreconditions()
  {
    ApplicationNames.checkValid(this.applicationName());
  }
}
