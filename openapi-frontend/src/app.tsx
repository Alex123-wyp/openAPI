import { LinkOutlined } from '@ant-design/icons';
import type { RequestConfig, RunTimeLayoutConfig } from '@umijs/max';
import { history, Link } from '@umijs/max';
import {
  AvatarDropdown,
  AvatarName,
  Footer,
  Question,
  SelectLang,
} from '@/components';
import { requestConfig } from './requestConfig';
//import type tells TypeScript: this is only for types, remove it from runtime output.
import type { InitialState } from './typings';
import { getLoginUserUsingGet } from './services/openapi-backend/userController';

const isDev = process.env.NODE_ENV === 'development';
const isDevOrTest = isDev || process.env.CI;
const loginPath = '/user/login';
const defaultAvatarSrc = '/default-avatar.jpg';
const appLogoSrc = '/api_icon.png';
const appTitle = 'Open API Platform';

/**
 * @see https://umijs.org/docs/api/runtime-config#getinitialstate
 * */
export async function getInitialState(): Promise<InitialState> {
  //Declare state is an object of InitialState
  const state: InitialState = {
    loginUser: undefined
  }

  try{
    const res = await getLoginUserUsingGet();
    if(res.data){
      state.loginUser = res.data;
    }
  }catch(error){
    history.push(loginPath);
  }
  return state;
  

  //当页面首次加载时，获取要全局保存的数据，比如用户的登录信息
  // const fetchUserInfo = async () => {
  //   try {
  //     const res = await getLoginUserUsingGet();
  //     if(res.data){
  //       state.loginUser = res.data;
  //     }
  //   } catch (error) {
  //     history.push(loginPath);
  //   }
  //       return state;
  // };



  // // 如果不是登录页面，执行
  // const { location } = history;
  // if (
  //   ![loginPath, '/user/register', '/user/register-result'].includes(
  //     location.pathname,
  //   )
  // ) {
  //   const currentUser = await fetchUserInfo();
  //   return {
  //     fetchUserInfo,
  //     currentUser,
  //     settings: defaultSettings as Partial<LayoutSettings>,
  //   };
  // }
  // return {
  //   fetchUserInfo,
  //   settings: defaultSettings as Partial<LayoutSettings>,
  // };
}

// ProLayout 支持的api https://procomponents.ant.design/components/layout
export const layout: RunTimeLayoutConfig = ({
  initialState,
  setInitialState,
}) => {
  const avatarSrc = initialState?.loginUser?.userAvatar?.trim() || defaultAvatarSrc;
  const renderBrand = () => (
    <Link
      key="app-brand"
      to="/"
      style={{
        display: 'flex',
        alignItems: 'center',
        gap: 12,
        color: 'inherit',
        textDecoration: 'none',
      }}
    >
      <img
        alt={appTitle}
        src={appLogoSrc}
        style={{
          width: 32,
          height: 32,
          objectFit: 'contain',
        }}
      />
      <span
        style={{
          fontSize: 18,
          fontWeight: 600,
          lineHeight: '24px',
          whiteSpace: 'nowrap',
        }}
      >
        {appTitle}
      </span>
    </Link>
  );

  return {
    logo: appLogoSrc,
    title: appTitle,
    actionsRender: () => [
      <Question key="doc" />,
      <SelectLang key="SelectLang" />,
    ],
    avatarProps: {
      src: avatarSrc,
      title: <AvatarName />,
      render: (_, avatarChildren) => (
        <AvatarDropdown>{avatarChildren}</AvatarDropdown>
      ),
    },
    waterMarkProps: {
      content: initialState?.loginUser?.userName,
    },
    footerRender: () => <Footer />,
    onPageChange: () => {
      const { location } = history;
      // 如果没有登录，重定向到 login
      if (!initialState?.loginUser && location.pathname !== loginPath) {
        history.push(loginPath);
      }
    },
    bgLayoutImgList: [
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/D2LWSqNny4sAAAAAAAAAAAAAFl94AQBr',
        left: 85,
        bottom: 100,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/C2TWRpJpiC0AAAAAAAAAAAAAFl94AQBr',
        bottom: -68,
        right: -45,
        height: '303px',
      },
      {
        src: 'https://mdn.alipayobjects.com/yuyan_qk0oxh/afts/img/F6vSTbj8KpYAAAAAAAAAAAAAFl94AQBr',
        bottom: 0,
        left: 0,
        width: '331px',
      },
    ],
    links: isDevOrTest
      ? [
          <Link key="openapi" to="/umi/plugin/openapi" target="_blank">
            <LinkOutlined />
            <span>OpenAPI 文档</span>
          </Link>,
        ]
      : [],
    menuHeaderRender: () => renderBrand(),
    headerTitleRender: () => renderBrand(),
    // 自定义 403 页面
    // unAccessible: <div>unAccessible</div>,
    // 增加一个 loading 的状态
    // childrenRender: (children) => {
    //   if (initialState?.loading) return <PageLoading />;
    //   return (
    //     <>
    //       {children}
    //       {isDevOrTest && (
    //         <SettingDrawer
    //           disableUrlParams
    //           enableDarkTheme
    //           settings={initialState?.settings}
    //           onSettingChange={(settings) => {
    //             setInitialState((preInitialState) => ({
    //               ...preInitialState,
    //               settings,
    //             }));
    //           }}
    //         />
    //       )}
    //     </>
    //   );
    // },
    // ...initialState?.settings,
  };
};

/**
 * @name request 配置，可以配置错误处理
 * 它基于 axios 和 ahooks 的 useRequest 提供了一套统一的网络请求和错误处理方案。
 * @doc https://umijs.org/docs/max/request#配置
 */
export const request: RequestConfig = {
  baseURL: isDev ? 'http://localhost:8283' : 'https://proapi.azurewebsites.net',
  ...requestConfig,
};
