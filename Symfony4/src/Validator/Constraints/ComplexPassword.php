<?php


namespace App\Validator\Constraints;

use Symfony\Component\Validator\Constraint;

/**
 * @Annotation
 */
class ComplexPassword extends Constraint
{
    public $message = 'user password must be complex';
}
