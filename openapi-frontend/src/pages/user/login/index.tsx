import { LockOutlined, UserOutlined } from '@ant-design/icons';
import {
  LoginForm,
  ProFormCheckbox,
  ProFormText,
} from '@ant-design/pro-components';
import {
  FormattedMessage,
  Helmet,
  SelectLang,
  history,
  useIntl,
  useModel,
} from '@umijs/max';
import { App, Typography } from 'antd';
import { createStyles } from 'antd-style';
import React, { useState } from 'react';
import { flushSync } from 'react-dom';
import { Footer } from '@/components';
import Settings from '../../../../config/defaultSettings';
import {
  userLoginUsingPost,
  userRegisterUsingPost,
} from '@/services/openapi-backend/userController';

type LoginCardMode = 'login' | 'register';

const useStyles = createStyles(({ token }) => {
  return {
    lang: {
      width: 42,
      height: 42,
      lineHeight: '42px',
      position: 'fixed',
      right: 16,
      borderRadius: token.borderRadius,
      ':hover': {
        backgroundColor: token.colorBgTextHover,
      },
    },
    container: {
      display: 'flex',
      flexDirection: 'column',
      height: '100vh',
      overflow: 'auto',
      backgroundImage:
        "url('https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/V-_oS6r-i7wAAAAAAAAAAAAAFl94AQBr')",
      backgroundSize: '100% 100%',
    },
    modeHeader: {
      marginBottom: 24,
      textAlign: 'center',
    },
    modeSwitch: {
      display: 'block',
      marginTop: 8,
      textAlign: 'center',
    },
  };
});

const Lang = () => {
  const { styles } = useStyles();

  return (
    <div className={styles.lang} data-lang>
      {SelectLang && <SelectLang />}
    </div>
  );
};

const Login: React.FC = () => {
  const { setInitialState } = useModel('@@initialState');
  const { styles } = useStyles();
  const { message } = App.useApp();
  const intl = useIntl();
  const [mode, setMode] = useState<LoginCardMode>('login');

  const switchMode = (nextMode: LoginCardMode) => {
    setMode(nextMode);
  };

  const handleLoginSubmit = async (values: API.UserLoginRequest) => {
    try {
      const res = await userLoginUsingPost({ ...values });
      if (res.data) {
        const urlParams = new URL(window.location.href).searchParams;
        const redirect = urlParams.get('redirect') || '/';

        flushSync(() => {
          setInitialState((state) => ({
            ...state,
            loginUser: res.data,
          }));
        });
        history.push(redirect);
        return;
      }
      message.error(
        intl.formatMessage({
          id: 'pages.login.failure',
          defaultMessage: 'Login failed, please try again!',
        }),
      );
    } catch (error) {
      const defaultLoginFailureMessage = intl.formatMessage({
        id: 'pages.login.failure',
        defaultMessage: 'Login failed, please try again!',
      });
      console.log(error);
      message.error(defaultLoginFailureMessage);
    }
  };

  const handleRegisterSubmit = async (values: API.UserRegisterRequest) => {
    if (values.userPassword !== values.checkPassword) {
      message.error(
        intl.formatMessage({
          id: 'pages.login.register.passwordMismatch',
          defaultMessage: 'Passwords do not match.',
        }),
      );
      return;
    }
    try {
      const res = await userRegisterUsingPost({ ...values });
      if (res.data) {
        message.success(
          intl.formatMessage({
            id: 'pages.login.register.success',
            defaultMessage: 'Registration successful! Please log in.',
          }),
        );
        switchMode('login');
        return;
      }
      message.error(
        intl.formatMessage({
          id: 'pages.login.register.failure',
          defaultMessage: 'Registration failed, please try again!',
        }),
      );
    } catch (error) {
      console.log(error);
      message.error(
        intl.formatMessage({
          id: 'pages.login.register.failure',
          defaultMessage: 'Registration failed, please try again!',
        }),
      );
    }
  };

  return (
    <div className={styles.container}>
      <Helmet>
        <title>
          {intl.formatMessage({
            id: 'menu.login',
            defaultMessage: 'Login',
          })}
          {Settings.title && ` - ${Settings.title}`}
        </title>
      </Helmet>
      <Lang />
      <div
        style={{
          flex: '1',
          padding: '32px 0',
        }}
      >
        <LoginForm
          key={mode}
          contentStyle={{
            minWidth: 280,
            maxWidth: '75vw',
          }}
          logo={<img alt="logo" src="/api_icon.png" />}
          title="Open API Platform"
          subTitle={intl.formatMessage({
            id: 'pages.layouts.userLayout.title',
          })}
          initialValues={{
            autoLogin: true,
          }}
          submitter={{
            searchConfig: {
              submitText:
                mode === 'login'
                  ? intl.formatMessage({
                      id: 'pages.login.submit',
                      defaultMessage: 'Login',
                    })
                  : intl.formatMessage({
                      id: 'pages.login.register.submit',
                      defaultMessage: 'Register',
                    }),
            },
          }}
          onFinish={async (values) => {
            if (mode === 'login') {
              await handleLoginSubmit(values as API.UserLoginRequest);
              return;
            }
            await handleRegisterSubmit(values as API.UserRegisterRequest);
          }}
        >
          <div className={styles.modeHeader}>
            <Typography.Title level={4} style={{ marginBottom: 0 }}>
              {mode === 'login'
                ? intl.formatMessage({
                    id: 'pages.login.accountLogin.tab',
                    defaultMessage: 'Account Login',
                  })
                : intl.formatMessage({
                    id: 'pages.login.register.title',
                    defaultMessage: 'Create Account',
                  })}
            </Typography.Title>
          </div>

          <ProFormText
            name="userAccount"
            fieldProps={{
              size: 'large',
              prefix: <UserOutlined />,
            }}
            placeholder={intl.formatMessage({
              id:
                mode === 'login'
                  ? 'pages.login.username.placeholder'
                  : 'pages.login.register.username.placeholder',
              defaultMessage:
                mode === 'login'
                  ? 'Username: admin or user'
                  : 'Choose a username',
            })}
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id={
                      mode === 'login'
                        ? 'pages.login.username.required'
                        : 'pages.login.register.username.required'
                    }
                    defaultMessage="Please input your username!"
                  />
                ),
              },
            ]}
          />
          <ProFormText.Password
            name="userPassword"
            fieldProps={{
              size: 'large',
              prefix: <LockOutlined />,
            }}
            placeholder={intl.formatMessage({
              id:
                mode === 'login'
                  ? 'pages.login.password.placeholder'
                  : 'pages.login.register.password.placeholder',
              defaultMessage:
                mode === 'login'
                  ? 'Password: ant.design'
                  : 'Create a password',
            })}
            rules={[
              {
                required: true,
                message: (
                  <FormattedMessage
                    id={
                      mode === 'login'
                        ? 'pages.login.password.required'
                        : 'pages.login.register.password.required'
                    }
                    defaultMessage="Please input your password!"
                  />
                ),
              },
            ]}
          />

          {mode === 'register' && (
            <ProFormText.Password
              name="checkPassword"
              fieldProps={{
                size: 'large',
                prefix: <LockOutlined />,
              }}
              placeholder={intl.formatMessage({
                id: 'pages.login.register.confirmPassword.placeholder',
                defaultMessage: 'Confirm your password',
              })}
              rules={[
                {
                  required: true,
                  message: (
                    <FormattedMessage
                      id="pages.login.register.confirmPassword.required"
                      defaultMessage="Please confirm your password!"
                    />
                  ),
                },
              ]}
            />
          )}

          {mode === 'login' && (
            <div
              style={{
                marginBottom: 24,
              }}
            >
              <ProFormCheckbox noStyle name="autoLogin">
                <FormattedMessage
                  id="pages.login.rememberMe"
                  defaultMessage="Remember me"
                />
              </ProFormCheckbox>
              <a
                style={{
                  float: 'right',
                }}
              >
                <FormattedMessage
                  id="pages.login.forgotPassword"
                  defaultMessage="Forgot Password?"
                />
              </a>
            </div>
          )}

          <a
            className={styles.modeSwitch}
            onClick={() => switchMode(mode === 'login' ? 'register' : 'login')}
          >
            <FormattedMessage
              id={
                mode === 'login'
                  ? 'pages.login.register.switch'
                  : 'pages.login.register.backToLogin'
              }
              defaultMessage={
                mode === 'login' ? 'Create an account' : 'Back to login'
              }
            />
          </a>
        </LoginForm>
      </div>
      <Footer />
    </div>
  );
};

export default Login;
