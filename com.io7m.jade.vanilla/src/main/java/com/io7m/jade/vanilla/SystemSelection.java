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

import org.apache.commons.lang3.SystemUtils;

/**
 * The system selection function.
 */

public final class SystemSelection
{
  private boolean isWindows;
  private boolean isUnix;

  /**
   * The system selection function.
   */

  public SystemSelection()
  {

  }

  /**
   * @return A system selection from the current system
   */

  public static SystemSelection fromSystem()
  {
    final var selection = new SystemSelection();
    selection.setUnix(SystemUtils.IS_OS_UNIX);
    selection.setWindows(SystemUtils.IS_OS_WINDOWS);
    return selection;
  }

  /**
   * @return {@code true} if the system is a Windows platform
   */

  public boolean isWindows()
  {
    return this.isWindows;
  }

  /**
   * Set whether or not the current platform is Windows.
   *
   * @param windows {@code true} if the current platform is Windows
   *
   * @return this
   */

  public SystemSelection setWindows(
    final boolean windows)
  {
    this.isWindows = windows;
    return this;
  }

  /**
   * @return {@code true} if the system is a UNIX-like platform
   */

  public boolean isUnix()
  {
    return this.isUnix;
  }

  /**
   * Set whether or not the current platform is UNIX-like.
   *
   * @param unix {@code true} if the current platform is UNIX-like
   *
   * @return this
   */

  public SystemSelection setUnix(
    final boolean unix)
  {
    this.isUnix = unix;
    return this;
  }
}
