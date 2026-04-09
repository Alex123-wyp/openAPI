// @ts-ignore
import { startMock } from '@@/requestRecordMock';
import { TestBrowser } from '@@/testBrowser';
import { fireEvent, render } from '@testing-library/react';
import React, { act } from 'react';

const waitTime = (time: number = 100) => {
  return new Promise((resolve) => {
    setTimeout(() => {
      resolve(true);
    }, time);
  });
};

let server: {
  close: () => void;
};

describe('Login Page', () => {
  beforeAll(async () => {
    server = await startMock({
      port: 8000,
      scene: 'login',
    });
  });

  afterAll(() => {
    server?.close();
  });

  it('should show login form', async () => {
    const historyRef = React.createRef<any>();
    const rootContainer = render(
      <TestBrowser
        historyRef={historyRef}
        location={{
          pathname: '/user/login',
        }}
      />,
    );

    await rootContainer.findAllByText('Open API Platform');

    act(() => {
      historyRef.current?.push('/user/login');
    });

    expect(
      rootContainer.baseElement?.querySelector('.ant-pro-form-login-desc')
        ?.textContent,
    ).toBe(
      'Build, manage, and invoke your APIs in one place.',
    );

    expect(rootContainer.queryByText('Phone Login')).toBeNull();
    expect(rootContainer.queryByText('Login with :')).toBeNull();
    expect(rootContainer.queryByText('Create an account')).toBeTruthy();
    expect(
      await rootContainer.findByPlaceholderText('Username: admin or user'),
    ).toBeTruthy();
    expect(
      await rootContainer.findByPlaceholderText('Password: ant.design'),
    ).toBeTruthy();

    rootContainer.unmount();
  });

  it('should switch to register form', async () => {
    const historyRef = React.createRef<any>();
    const rootContainer = render(
      <TestBrowser
        historyRef={historyRef}
        location={{
          pathname: '/user/login',
        }}
      />,
    );

    await rootContainer.findAllByText('Open API Platform');

    await act(async () => {
      fireEvent.click(await rootContainer.findByText('Create an account'));
    });

    expect(rootContainer.queryByText('Account Login')).toBeNull();
    expect(await rootContainer.findByText('Create Account')).toBeTruthy();
    expect(
      await rootContainer.findByPlaceholderText('Choose a username'),
    ).toBeTruthy();
    expect(
      await rootContainer.findByPlaceholderText('Create a password'),
    ).toBeTruthy();
    expect(
      await rootContainer.findByPlaceholderText('Confirm your password'),
    ).toBeTruthy();
    expect(await rootContainer.findByText('Back to login')).toBeTruthy();

    rootContainer.unmount();
  });

  it('should login success', async () => {
    const historyRef = React.createRef<any>();
    const rootContainer = render(
      <TestBrowser
        historyRef={historyRef}
        location={{
          pathname: '/user/login',
        }}
      />,
    );

    await rootContainer.findAllByText('Open API Platform');

    const userNameInput = await rootContainer.findByPlaceholderText(
      'Username: admin or user',
    );

    act(() => {
      fireEvent.change(userNameInput, { target: { value: 'admin' } });
    });

    const passwordInput = await rootContainer.findByPlaceholderText(
      'Password: ant.design',
    );

    act(() => {
      fireEvent.change(passwordInput, { target: { value: 'ant.design' } });
    });

    await (await rootContainer.findByText('Login')).click();

    // 等待接口返回结果
    await waitTime(5000);

    await rootContainer.findAllByText('Open API Platform');

    await waitTime(2000);

    rootContainer.unmount();
  });
});
