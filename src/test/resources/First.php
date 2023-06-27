<?php

declare(strict_types=1);

namespace Asdf\Zxcv;

use Blah\Bhah;
use Blah\Sdds;
use Blah\Zcdf;


final class First extends Sdds implements Bhah
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
    function doSmthWithResult($asdf): Zcdf
    {
        return new Zcdf();
    }

    /**
     * @var Blah $asdf
     */
    function doSmthWithResultDef($asdf = null): Zcdf
    {
        return new Zcdf();
    }

    /**
     * @var Blah $asdf
     */
    function doSmthWithResultDefRef(&$asdf = null): Zcdf
    {
        return new Zcdf();
    }
}