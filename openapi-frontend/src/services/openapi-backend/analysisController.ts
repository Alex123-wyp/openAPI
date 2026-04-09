// @ts-ignore
/* eslint-disable */
import { request } from "@umijs/max";

/** listTopInvokerInterfaceInfo GET /api/analysis/top/interface/invoke */
export async function listTopInvokerInterfaceInfoUsingGet(options?: {
  [key: string]: any;
}) {
  return request<API.BaseResponseListListTopInvokerInterfaceInfoVO_>(
    "/api/analysis/top/interface/invoke",
    {
      method: "GET",
      ...(options || {}),
    }
  );
}
