<?php

declare(strict_types=1);

namespace Asdf\Zxcv;

use Blah\Bhah;
use Blah\Sdds;
use Blah\Zcdf;


abstract class First
{
    const asdf = [];
    /** @var One */
    private $one;
    /** @var Sdds */
    private $sdds;

    public function __construct(One $one, Sdds $sdds)
    {
        $this->one = $one;
        $this->sdds = $sdds;
    }

    function init(): void
    {

    }

    /**
     * @var Blah $asdf
     */
    abstract protected function doSmthWithResult($asdf): Zcdf;

    /**
     * @var Blah $asdf
     */
    abstract protected function doSmthWithResultArr($asdf): array;
}