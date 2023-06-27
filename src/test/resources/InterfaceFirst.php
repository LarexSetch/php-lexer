<?php

declare(strict_types=1);

namespace Asdf\Zxcv;

use Blah\Bhah;
use Blah\Sdds;
use Blah\Zcdf;


interface First extends AnotherIface
{
    const SOME_CONST = "asdf";

    /**
     * @var Blah $asdf
     */
    public function doSmthWithResult($asdf): Zcdf;

    /**
     * @var Blah $asdf
     *
     * @return self
     */
    public function doSmthWithResultArr($asdf): self;
}