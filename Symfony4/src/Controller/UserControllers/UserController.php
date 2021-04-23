<?php


namespace App\Controller;


use App\Entity\Users;
use App\Form\Security\LoginType;
use App\Form\User\EditType;
use App\Form\User\RegistrationType;
use App\Form\User\RequestResetPasswordType;
use App\Form\User\ResetPasswordType;
use App\Form\UserType;
use App\Security\LoginFormAuthenticator;
use App\Service\CaptchaValidator;
use App\Service\FileUploader;
use App\Service\Mailer;
use App\Service\TokenGenerator;
use Sensio\Bundle\FrameworkExtraBundle\Configuration\Security;
use Symfony\Bundle\FrameworkBundle\Controller\AbstractController;
use Symfony\Component\Form\FormError;
use Symfony\Component\HttpFoundation\File\UploadedFile;
use Symfony\Component\HttpFoundation\Request;
use Symfony\Component\HttpFoundation\Response;
use Symfony\Component\Routing\Annotation\Route;
use Symfony\Component\Security\Core\Encoder\UserPasswordEncoderInterface;
use Symfony\Component\Security\Guard\GuardAuthenticatorHandler;
use Symfony\Component\Security\Http\Authentication\AuthenticationUtils;
use Symfony\Component\Validator\Exception\ValidatorException;
use Symfony\Contracts\Translation\TranslatorInterface;

/**
 * @Route("/user", name="user_")
 */
class UserController extends AbstractController
{
    const configMail = true;

    /**
     * @Route("/register", name="register")
     * @param FileUploader $fileUploader
     * @param Request $request
     * @param TokenGenerator $tokenGenerator
     * @param UserPasswordEncoderInterface $encoder
     * @param Mailer $mailer
     * @param AuthenticationUtils $authenticationUtils
     * @param CaptchaValidator $captchaValidator
     * @param TranslatorInterface $translator
     * @return Response
     * @throws \Throwable
     */
    public function register(FileUploader $fileUploader, Request $request, TokenGenerator $tokenGenerator, UserPasswordEncoderInterface $encoder,
                             Mailer $mailer, AuthenticationUtils $authenticationUtils, CaptchaValidator $captchaValidator, TranslatorInterface $translator)
    {
        if ($this->isGranted('IS_AUTHENTICATED_FULLY')) {
            return $this->redirect($this->generateUrl('homepage'));
        }
        $form = $this->createForm(RegistrationType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var Users $user */
            $user = $form->getData();

            try {
               if (!$captchaValidator->validateCaptcha($request->get('g-recaptcha-response'))) {
                    $form->addError(new FormError('captcha wrong'));
                    throw new ValidatorException('captcha.wrong');
                }
                /** @var UploadedFile $certificationFile */
                $certificationFile = $form->get('imageName')->getData();
                if ($certificationFile) {
                    $newFilename = $fileUploader->upload($certificationFile);
                    $user->setImageName($newFilename);
                }
                $user->setPassword($encoder->encodePassword($user, $user->getPassword()));
                $token = $tokenGenerator->generateToken();
                $user->setToken($token);
                $user->setRoles(array("ROLE_USER"));
                $user->setIsActive(false);
                $em = $this->getDoctrine()->getManager();
                $em->persist($user);
                $em->flush();

                if (self::configMail) {
                    $mailer->sendActivationEmailMessage($user);
                    $this->addFlash('success', 'confirmation link is sent tou you');
                    return $this->redirect($this->generateUrl('homepage'));
                }

                return $this->redirect($this->generateUrl('user_activate', ['token' => $token]));

            } catch (ValidatorException $exception) {

            }
        }


        $formLogin = $this->createForm(LoginType::class);
        //login errorss
        // get the login error if there is one
        $formLogin->handleRequest($request);


        // last username entered by the user
        $error = $authenticationUtils->getLastAuthenticationError();


        return $this->render('user/register.html.twig', [
            'form' => $form->createView(),
            'error' => $error,
            'formlogin' => $formLogin->createView(),

            'captchakey' => $captchaValidator->getKey()
        ]);
    }

    /**
     * @Route("/activate/{token}", name="activate")
     * @param $request Request
     * @param $user Users
     * @param GuardAuthenticatorHandler $authenticatorHandler
     * @param LoginFormAuthenticator $loginFormAuthenticator
     * @return Response
     * @throws \Exception
     */
    public function activate(Request $request, Users $user, GuardAuthenticatorHandler $authenticatorHandler, LoginFormAuthenticator $loginFormAuthenticator)
    {
        $user->setIsActive(true);
        $user->setToken(null);
        $user->setActivatedAt(new \DateTime());

        $em = $this->getDoctrine()->getManager();
        $em->persist($user);
        $em->flush();

        $this->addFlash('success', 'Welcome');

        // automatic login
        return $authenticatorHandler->authenticateUserAndHandleSuccess(
            $user,
            $request,
            $loginFormAuthenticator,
            'main'
        );
    }

    /**
     * @Route("/edit", name="edit")
     * @Security("has_role('ROLE_USER')")
     * @param $request Request
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     */
    public function edit(Request $request, UserPasswordEncoderInterface $encoder)
    {
        $origPwd = $this->getUser()->getPassword();
        $form = $this->createForm(EditType::class, $this->getUser());
        $form->handleRequest($request);

        if ($form->isSubmitted()) {
            /** @var Users $user */
            $user = $form->getData();
            $pwd = $user->getPassword() ? $encoder->encodePassword($user, $user->getPassword()) : $origPwd;
            $user->setPassword($pwd);
            $em = $this->getDoctrine()->getManager();

            if ($form->isValid()) {
                $em->persist($user);
                $em->flush();
                $this->addFlash('success', 'user updated successfully');

                return $this->redirect($this->generateUrl('homepage'));
            }

            $em->refresh($user);
        }

        return $this->render('user/edit.html.twig', [
            'form' => $form->createView()
        ]);
    }

    /**
     * @Route("/request-password-reset", name="request_password_reset")
     * @param Request $request
     * @param TokenGenerator $tokenGenerator
     * @param Mailer $mailer
     * @param TranslatorInterface $translator
     * @return Response
     * @throws \Throwable
     */
    public function requestPasswordReset(Request $request, TokenGenerator $tokenGenerator, Mailer $mailer,
                                         TranslatorInterface $translator)
    {
        if ($this->isGranted('IS_AUTHENTICATED_REMEMBERED')) {
            return $this->redirect($this->generateUrl('homepage'));
        }

        $form = $this->createForm(RequestResetPasswordType::class);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {

            try {

                $repository = $this->getDoctrine()->getRepository(Users::class);

                /** @var Users $user */
                $user = $repository->findOneBy(['email' => $form->get('email')->getData(), 'isActive' => true]);
                if (!$user) {
                    $this->addFlash('warning', 'user not-found');
                    return $this->render('user/request-password-reset.html.twig', [
                        'form' => $form->createView(),
                    ]);
                }

                $token = $tokenGenerator->generateToken();
                $user->setToken($token);
                $em = $this->getDoctrine()->getManager();
                $em->persist($user);
                $em->flush();

                $mailer->sendResetPasswordEmailMessage($user);

                $this->addFlash('success', 'Password link sent successfully');
                return $this->redirect($this->generateUrl('homepage'));
            } catch (ValidatorException $exception) {

            }
        }

        return $this->render('user/request-password-reset.html.twig', [
            'form' => $form->createView(),
        ]);
    }

    /**
     * @Route("/reset-password/{token}", name="reset_password")
     * @param $request Request
     * @param $user Users
     * @param $authenticatorHandler GuardAuthenticatorHandler
     * @param $loginFormAuthenticator LoginFormAuthenticator
     * @param UserPasswordEncoderInterface $encoder
     * @return Response
     */
    public function resetPassword(Request $request, Users $user, GuardAuthenticatorHandler $authenticatorHandler,
                                  LoginFormAuthenticator $loginFormAuthenticator, UserPasswordEncoderInterface $encoder)
    {
        if ($this->isGranted('IS_AUTHENTICATED_REMEMBERED')) {
            return $this->redirect($this->generateUrl('homepage'));
        }

        $form = $this->createForm(ResetPasswordType::class, $user);
        $form->handleRequest($request);

        if ($form->isSubmitted() && $form->isValid()) {
            /** @var Users $user */
            $user = $form->getData();
            $user->setPassword($encoder->encodePassword($user, $user->getPassword()));
            $user->setToken(null);

            $em = $this->getDoctrine()->getManager();
            $em->persist($user);
            $em->flush();

            $this->addFlash('success', 'User updated successfully');

            // automatic login
            return $authenticatorHandler->authenticateUserAndHandleSuccess(
                $user,
                $request,
                $loginFormAuthenticator,
                'main'
            );
        }

        return $this->render('user/password-reset.html.twig', ['form' => $form->createView()]);
    }

    /**
     * @Route("/profile-edit", name="editprofile")
     * @param Request $request
     * @param FileUploader $fileUploader
     * @return \Symfony\Component\HttpFoundation\RedirectResponse|Response
     */
    public function editUserInfo(Request $request, FileUploader $fileUploader)
    {
        if (!$this->isGranted('IS_AUTHENTICATED_FULLY')) {
            $this->redirectToRoute('security_login');}
        $em = $this->getDoctrine()->getManager();
        $user = $this->getUser();
        $form = $this->createForm(UserType::class, $user);
        $form->handleRequest($request);
        if ($form->isSubmitted() && $form->isValid()) {
            /**
             * @var UploadedFile $image
             */
            $image = $form->get('imageName')->getData();
            if ($image) {
                $newFilename = $fileUploader->upload($image);
                $user->setImageName($newFilename);
            }
            $em->persist($user);

            $em->flush();
            return $this->redirectToRoute('homepage');
        }
        return $this->render("user/edit.html.twig", [
            'form' => $form->createView()
        ]);


    }
}
